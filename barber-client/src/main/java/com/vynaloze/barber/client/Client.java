package com.vynaloze.barber.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final PrintWriter outputStream;
    private Socket socket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public Client(final PrintWriter outputStream) {
        this.outputStream = outputStream;
    }

    public void connect(final String host, final int port) {
        try {
            socket = new Socket(host, port);
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            final Thread reader = new Thread(new SocketReader(socketIn, outputStream));
            reader.setDaemon(true);
            reader.start();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + host);
            System.exit(1);
        }
    }

    public void send(String input) {
        outputStream.println("<<<" + input);
        socketOut.println(input);
    }
}
