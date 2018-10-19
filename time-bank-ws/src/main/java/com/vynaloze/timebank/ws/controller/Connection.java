package com.vynaloze.timebank.ws.controller;

import com.vynaloze.timebank.common.Command;
import com.vynaloze.timebank.ws.pojo.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final Controller controller;

    public Connection(final Socket socket, final Controller controller) {
        this.socket = socket;
        this.controller = controller;
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

                while ((inputLine = in.readLine()) != null) {
                    final Response response = controller.process(inputLine);
                    outputLine = response.getResponse();
                    out.println(outputLine);
                    if (outputLine.equalsIgnoreCase(Command.DROP.toString())) {
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