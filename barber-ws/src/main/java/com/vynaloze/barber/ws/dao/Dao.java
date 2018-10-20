package com.vynaloze.barber.ws.dao;

import com.vynaloze.barber.ws.pojo.Appointment;

import java.util.List;

public interface Dao {
    List<Appointment> getAll();

    Appointment reserve(int hour, String client, long sleep) throws AppointmentNotFoundException, AppointmentAlreadyReservedException, LockTimeoutException;

    Appointment cancel(int hour) throws AppointmentNotFoundException, LockTimeoutException;
}
