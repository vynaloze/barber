package com.vynaloze.timebank.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final Controller controller;

    public Connection(Socket socket, Controller controller) {
        this.socket = socket;
        this.controller = controller;
        Thread thread = new Thread(new Runner());
        thread.setDaemon(true);
        thread.start();
    }

    private class Runner implements Runnable {
        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String inputLine, outputLine;

                out.println("Connected.");
                System.out.println("Client connected.");

                while ((inputLine = in.readLine()) != null) {
                    Response response = controller.process(inputLine);
                    outputLine = response.getResponse();
                    out.println(outputLine);
                    if (outputLine.equals("DROP")) { //fixme
                        break;
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}