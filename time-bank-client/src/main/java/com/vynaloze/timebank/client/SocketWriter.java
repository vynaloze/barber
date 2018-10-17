package com.vynaloze.timebank.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SocketWriter implements Runnable {
    private BufferedReader in;
    private PrintWriter out;

    public SocketWriter(BufferedReader in, PrintWriter out) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
