package com.vynaloze.barber.ws.controller;

import java.io.PrintWriter;
import java.util.Optional;

public class BroadcasterReader implements Runnable {
    private final Broadcaster broadcaster;
    private final int id;
    private final PrintWriter out;

    public BroadcasterReader(final Broadcaster broadcaster, final int id, final PrintWriter out) {
        this.broadcaster = broadcaster;
        this.id = id;
        this.out = out;
    }

    @Override
    public void run() {
        Optional<String> broadcastedLine;
        while (true) {
            broadcastedLine = broadcaster.getMessage(id);
            broadcastedLine.ifPresent(out::println);
        }
    }
}
