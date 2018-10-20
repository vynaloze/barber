package com.vynaloze.barber.ws.controller;

import com.vynaloze.barber.common.Command;
import com.vynaloze.barber.ws.pojo.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Connection {
    private static final AtomicInteger connectionCounter = new AtomicInteger();
    private final int id;
    private final Socket socket;
    private final Controller controller;
    private final Broadcaster broadcaster;

    public Connection(final Socket socket, final Controller controller, final Broadcaster broadcaster) {
        this.id = connectionCounter.getAndIncrement();
        this.socket = socket;
        this.controller = controller;
        this.broadcaster = broadcaster;
        this.broadcaster.addClient(id);

        final Thread thread = new Thread(new Runner());
        thread.setDaemon(true);
        thread.start();
    }

    private class Runner implements Runnable {
        @Override
        public void run() {
            try (final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 final BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String inputLine, outputLine;

                out.println("Connected.");
                System.out.println("Client connected.");

                Thread broadcasterReader = new Thread(new BroadcasterReader(broadcaster, id, out));
                broadcasterReader.setDaemon(true);
                broadcasterReader.start();

                while ((inputLine = in.readLine()) != null) {
                    final Response response = controller.process(inputLine);
                    outputLine = response.getResponse();
                    if (response.isBroadcast()) {
                        broadcaster.pushMessage(outputLine);
                    } else {
                        out.println(outputLine);
                    }

                    if (outputLine.equalsIgnoreCase(Command.DROP.toString())) {
                        broadcasterReader.interrupt();
                        break;
                    }
                }
                socket.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}