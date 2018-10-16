package com.vynaloze.timebank.ws;

import javax.annotation.Nonnull;

public class Response {
    @Nonnull
    private final String response;
    private final boolean broadcast;

    public Response(@Nonnull final String response, final boolean broadcast) {
        this.response = response;
        this.broadcast = broadcast;
    }

    @Nonnull
    public String getResponse() {
        return response;
    }

    public boolean isBroadcast() {
        return broadcast;
    }
}
