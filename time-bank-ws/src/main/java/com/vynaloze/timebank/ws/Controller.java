package com.vynaloze.timebank.ws;

import com.vynaloze.timebank.common.Request;

public class Controller {

    public String process(String input) {
        Request request = Protocol.toRequest(input);
        switch (request.getType()) {
            case GET:
            case POST:
            case DELETE:
            case DROP:
                return Request.Type.DROP.toString();
        }
        return "ERROR";
    }
}
