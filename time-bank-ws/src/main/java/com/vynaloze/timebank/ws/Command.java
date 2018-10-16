package com.vynaloze.timebank.ws;

public enum Command {
    GET(1), POST(5), RESERVE(3), DELETE(2), DROP(1), INVALID(0);

    private int requestLength;

    Command(int requestLength) {
        this.requestLength = requestLength;
    }

    public int getRequestLength() {
        return requestLength;
    }
}
