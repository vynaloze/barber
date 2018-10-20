package com.vynaloze.barber.ws.dao;

import com.vynaloze.barber.ws.pojo.Appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static com.vynaloze.barber.ws.pojo.Appointment.CLOSE_HOUR;
import static com.vynaloze.barber.ws.pojo.Appointment.OPEN_HOUR;

public class DaoImpl implements Dao {
    private final List<Appointment> appointments;
    private final ReentrantLock lock = new ReentrantLock();

    public DaoImpl() {
        appointments = new ArrayList<>();
        for (int i = OPEN_HOUR; i <= CLOSE_HOUR; i++) {
            appointments.add(new Appointment(i));
        }
    }

    @Override
    public List<Appointment> getAll() {
        return appointments;
    }

    @Override
    public Appointment reserve(int hour, String client, long sleep)
            throws AppointmentNotFoundException, AppointmentAlreadyReservedException, LockTimeoutException {
        try {
            if (lock.tryLock(LockTimeoutException.TIMEOUT, TimeUnit.MILLISECONDS)) {
                Thread.sleep(sleep);
                Appointment appointment = appointments.stream().filter(a -> a.getHour() == hour).findFirst().orElseThrow(AppointmentNotFoundException::new);
                if (appointment.getClient().isPresent()) {
                    throw new AppointmentAlreadyReservedException();
                }
                appointment.setClient(client);
                return appointment;
            } else {
                throw new LockTimeoutException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        throw new RuntimeException("Sorry. This should never happened.");
    }

    @Override
    public Appointment cancel(int hour) throws AppointmentNotFoundException, LockTimeoutException {
        try {
            if (lock.tryLock(LockTimeoutException.TIMEOUT, TimeUnit.MILLISECONDS)) {
                Appointment appointment = appointments.stream().filter(a -> a.getHour() == hour).findFirst().orElseThrow(AppointmentNotFoundException::new);
                appointment.setClient(null);
                return appointment;
            } else {
                throw new LockTimeoutException();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        throw new RuntimeException("Sorry. This should never happened.");
    }
}
