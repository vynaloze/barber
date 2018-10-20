package com.vynaloze.barber.ws.dao;

import com.vynaloze.barber.ws.pojo.Appointment;

import java.util.List;

public interface Dao {
    List<Appointment> getAll();

    Appointment getByHour(int hour) throws AppointmentNotFoundException;

    Appointment reserve(int hour, String client) throws AppointmentNotFoundException, AppointmentAlreadyReservedException;

    Appointment cancel(int hour) throws AppointmentNotFoundException;
}
