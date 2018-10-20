package com.vynaloze.barber.ws.pojo;

public class Response {
    private final String response;
    private final boolean broadcast;

    public Response(final String response, final boolean broadcast) {
        this.response = response;
        this.broadcast = broadcast;
    }

    public String getResponse() {
        return response;
    }

    public boolean isBroadcast() {
        return broadcast;
    }
}
