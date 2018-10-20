package com.vynaloze.barber.ws.dao;

import com.vynaloze.barber.ws.pojo.Appointment;

import java.util.ArrayList;
import java.util.List;

import static com.vynaloze.barber.ws.pojo.Appointment.CLOSE_HOUR;
import static com.vynaloze.barber.ws.pojo.Appointment.OPEN_HOUR;

public class DaoImpl implements Dao {
    private final List<Appointment> appointments;

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
    public Appointment getByHour(int hour) throws AppointmentNotFoundException {
        return appointments.stream().filter(a -> a.getHour() == hour).findFirst().orElseThrow(AppointmentNotFoundException::new);

    }

    @Override
    public Appointment reserve(int hour, String client) throws AppointmentNotFoundException, AppointmentAlreadyReservedException {
        Appointment appointment = appointments.stream().filter(a -> a.getHour() == hour).findFirst().orElseThrow(AppointmentNotFoundException::new);
        if (appointment.getClient().isPresent()) {
            throw new AppointmentAlreadyReservedException();
        }
        appointment.setClient(client);
        return appointment;
    }

    @Override
    public Appointment cancel(int hour) throws AppointmentNotFoundException {
        Appointment appointment = appointments.stream().filter(a -> a.getHour() == hour).findFirst().orElseThrow(AppointmentNotFoundException::new);
        appointment.setClient(null);
        return appointment;
    }
}
