package com.vynaloze.timebank.ws;

import com.vynaloze.timebank.common.Service;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    private final Dao dao;

    public Controller(Dao dao) {
        this.dao = dao;
    }

    public Response process(String request) {
        String[] splitted = request.split(";");
        Command command = getCommandAndValidate(splitted);

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

    private Command getCommandAndValidate(String[] splitted) {
        try {
            Command type = Command.valueOf(splitted[0].toUpperCase());
            if (type.getRequestLength() == splitted.length) {
                return type;
            }
        } catch (IllegalArgumentException ignored) {
        }
        return Command.INVALID;
    }

    private String get() {
        List<Service> services = dao.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Service service : services) {
            stringBuilder.append(service).append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }

    private String post(String[] request) {
        String contractor = request[1];
        LocalDate date = LocalDate.parse(request[2], Service.getDateFormatter());
        String title = request[3];
        String details = request[4];
        Service service = new Service(contractor, date, title, details);
        return dao.postService(service).toString();
    }

    private String reserve(String[] request) {
        try {
            return dao.reserveService(Integer.valueOf(request[1]), request[2]).toString();
        } catch (ServiceNotFoundException | ServiceAlreadyReservedException e) {
            return e.toString();
        }
    }

    private String delete(String[] request) {
        try {
            return dao.deleteService(Integer.valueOf(request[1])).toString();
        } catch (ServiceNotFoundException e) {
            return e.toString();
        }
    }
}
