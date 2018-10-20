package com.vynaloze.barber.ws.controller;

import com.vynaloze.barber.common.Command;
import com.vynaloze.barber.ws.dao.AppointmentAlreadyReservedException;
import com.vynaloze.barber.ws.dao.AppointmentNotFoundException;
import com.vynaloze.barber.ws.dao.Dao;
import com.vynaloze.barber.ws.dao.LockTimeoutException;
import com.vynaloze.barber.ws.pojo.Appointment;
import com.vynaloze.barber.ws.pojo.Response;

import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private final Dao dao;
    private final long sleep;

    public Controller(Dao dao, long sleep) {
        this.dao = dao;
        this.sleep = sleep;
    }

    public Response process(final String request) {
        final String[] splitted = request.split(";");
        final Command command = getCommandAndValidate(splitted);

        if (command.equals(Command.INVALID)) {
            return new Response("Incorrect length or did not recognise command", false);
        }

        switch (command) {
            case GET:
                return get();
            case RESERVE:
                return reserve(splitted);
            case CANCEL:
                return cancel(splitted);
            case DROP:
                return new Response(Command.DROP.toString(), false);
        }
        return new Response("Oops... This should never happen.", false);
    }

    private Command getCommandAndValidate(final String[] splitted) {
        try {
            final Command type = Command.valueOf(splitted[0].toUpperCase());
            if (type.getRequestLength() == splitted.length) {
                return type;
            }
        } catch (final IllegalArgumentException ignored) {
        }
        return Command.INVALID;
    }

    private Response get() {
        final List<String> services = dao.getAll().stream().map(Appointment::toString).collect(Collectors.toList());
        return new Response(String.join(System.getProperty("line.separator"), services), false);
    }

    private Response reserve(final String[] request) {
        try {
            String appointment = dao.reserve(Integer.valueOf(request[1]), request[2], sleep).toString();
            String prefix = "RESERVED ";
            return new Response(prefix + appointment, true);
        } catch (final AppointmentNotFoundException | AppointmentAlreadyReservedException | LockTimeoutException e) {
            return new Response(e.getClass().getSimpleName(), false);
        }
    }

    private Response cancel(final String[] request) {
        try {
            String appointment = dao.cancel(Integer.valueOf(request[1])).toString();
            String prefix = "CANCELLED ";
            return new Response(prefix + appointment, true);
        } catch (final AppointmentNotFoundException | LockTimeoutException e) {
            return new Response(e.getClass().getSimpleName(), false);
        }
    }
}
