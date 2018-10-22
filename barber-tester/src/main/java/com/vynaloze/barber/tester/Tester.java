package com.vynaloze.barber.tester;

import com.vynaloze.barber.client.Client;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

public class Tester {
    private Client client;
    StringWriter out;
    private List<String> output = new LinkedList<>();

    public Tester() {
        out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        this.client = new Client(writer);
        client.connect("localhost", 8080);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    client.send(RandomRequests.random());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public String getOutput() {
        return String.join(System.getProperty("line.separator"), output);
    }

    public void addToOutput(String string) {
        output.add(string);
    }

    public String foo() {
        return "\033[H\033[2J" + out.toString();
    }
}
