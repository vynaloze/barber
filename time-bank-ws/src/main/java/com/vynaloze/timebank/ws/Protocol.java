package com.vynaloze.timebank.ws;

import com.vynaloze.timebank.common.Request;
import com.vynaloze.timebank.common.Service;
import com.vynaloze.timebank.common.User;

import java.time.LocalDate;

public class Protocol {
    public static Request toRequest(String input) {
        if (!input.contains(";")) {
            return new Request(Request.Type.INVALID);
        }
        String[] splitted = input.split(";");
        Request.Type type = Request.Type.valueOf(splitted[0].toUpperCase());
        if (type.equals(Request.Type.DROP) || type.equals(Request.Type.GET)) {
            return new Request(type);
        } else {
            if (splitted.length != 6) {
                return new Request(Request.Type.INVALID);
            }
            User user = new User(splitted[1], User.Role.getRole(splitted[2]));
            LocalDate date = LocalDate.parse(splitted[3], Service.getDateFormatter());
            String title = splitted[4];
            String details = splitted[5];
            Service service = new Service(user, date, title, details);
            return new Request(type, service);
        }
    }

    public static String toString(Request request) {
        return request.getType() + ";" + (request.getService() == null ? "" : request.getService().toString());
    }
}
