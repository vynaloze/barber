package com.vynaloze.barber.common;

public enum Command {
    GET(1), RESERVE(3), CANCEL(2), DROP(1), INVALID(0);

    private final int requestLength;

    Command(final int requestLength) {
        this.requestLength = requestLength;
    }

    public int getRequestLength() {
        return requestLength;
    }
}
