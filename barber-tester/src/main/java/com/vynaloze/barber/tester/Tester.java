package com.vynaloze.barber.tester;

import com.vynaloze.barber.client.Client;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Tester implements Runnable {
    private final Client client;
    private final StringWriter out;
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public Tester() {
        this.out = new StringWriter();
        final PrintWriter writer = new PrintWriter(out);

        this.client = new Client(writer);
        client.connect(HOST, PORT);
    }

    @Override
    public void run() {
        while (true) {
            final int oldLength = getLines().length;
            final long start = System.currentTimeMillis();
            client.send(RandomRequests.random());
            while (System.currentTimeMillis() - start < 10_000
                    || oldLength == getLines().length) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String[] getLines() {
        return out.toString().split("\\r?\\n");
    }
}
