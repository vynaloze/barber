package com.vynaloze.timebank.ws.controller;

import com.vynaloze.timebank.common.Command;
import com.vynaloze.timebank.ws.dao.Dao;
import com.vynaloze.timebank.ws.dao.ServiceAlreadyReservedException;
import com.vynaloze.timebank.ws.dao.ServiceNotFoundException;
import com.vynaloze.timebank.ws.pojo.Response;
import com.vynaloze.timebank.ws.pojo.Service;

import java.time.LocalDate;
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
            case POST:
                return new Response(post(splitted), true);
            case RESERVE:
                return new Response(reserve(splitted), true);
            case DELETE:
                return new Response(delete(splitted), true);
            case DROP:
                return new Response("DROP", false); //fixme
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
        final List<String> services = dao.getAll().stream().map(Service::toString).collect(Collectors.toList());
        return String.join(System.getProperty("line.separator"), services);
    }

    private String post(final String[] request) {
        final String contractor = request[1];
        final LocalDate date = LocalDate.parse(request[2], Service.getDateFormatter());
        final String title = request[3];
        final String details = request[4];
        final Service service = new Service(contractor, date, title, details);
        return dao.postService(service).toString();
    }

    private String reserve(final String[] request) {
        try {
            return dao.reserveService(Integer.valueOf(request[1]), request[2]).toString();
        } catch (final ServiceNotFoundException | ServiceAlreadyReservedException e) {
            return e.toString();
        }
    }

    private String delete(final String[] request) {
        try {
            return dao.deleteService(Integer.valueOf(request[1])).toString();
        } catch (final ServiceNotFoundException e) {
            return e.toString();
        }
    }
}
