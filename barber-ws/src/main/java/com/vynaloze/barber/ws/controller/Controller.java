package com.vynaloze.barber.ws.controller;

import com.vynaloze.barber.common.Command;
import com.vynaloze.barber.ws.dao.AppointmentAlreadyReservedException;
import com.vynaloze.barber.ws.dao.AppointmentNotFoundException;
import com.vynaloze.barber.ws.dao.Dao;
import com.vynaloze.barber.ws.pojo.Appointment;
import com.vynaloze.barber.ws.pojo.Response;

import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private final Dao dao;

    public Controller(final Dao dao) {
        this.dao = dao;
    }

    public Response process(final String request) {
        final String[] splitted = request.split(";");
        final Command command = getCommandAndValidate(splitted);

        if (command.equals(Command.INVALID)) {
            return new Response("Incorrect length or did not recognise command", false);
        }

        switch (command) {
            case GET:
                return new Response(get(), false);
            case RESERVE:
                return new Response(reserve(splitted), true);
            case CANCEL:
                return new Response(delete(splitted), true);
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

    private String get() {
        final List<String> services = dao.getAll().stream().map(Appointment::toString).collect(Collectors.toList());
        return String.join(System.getProperty("line.separator"), services);
    }

    private String reserve(final String[] request) {
        try {
            return dao.reserve(Integer.valueOf(request[1]), request[2]).toString();
        } catch (final AppointmentNotFoundException | AppointmentAlreadyReservedException e) {
            return e.toString();
        }
    }

    private String delete(final String[] request) {
        try {
            return dao.cancel(Integer.valueOf(request[1])).toString();
        } catch (final AppointmentNotFoundException e) {
            return e.toString();
        }
    }
}
