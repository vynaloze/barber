package com.vynaloze.barber.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SocketWriter implements Runnable {
    private final BufferedReader in;
    private final PrintWriter out;

    public SocketWriter(final BufferedReader in, final PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        String input;
        try {
            while ((input = in.readLine()) != null) {
                System.out.println("<<<" + input);
                out.println(input);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
