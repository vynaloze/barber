package com.vynaloze.timebank.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SocketReader implements Runnable {
    private BufferedReader in;
    private PrintWriter out;

    public SocketReader(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
    } //fixme -> some generics maybe?

    @Override
    public void run() {
        String input;
        try {
            while ((input = in.readLine()) != null) {
                out.println(">>>" + input);
                if (input.equals("DROP")) { //fixme
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
