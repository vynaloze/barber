package com.vynaloze.barber.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(final String[] args) {
        final String hostName = "localhost";
        final int portNumber = 8080;

        try (final Socket socket = new Socket(hostName, portNumber);
             final BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             final PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
             final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
             final PrintWriter stdOut = new PrintWriter(System.out, true)) {

            final Thread reader = new Thread(new SocketReader(socketIn, stdOut));
            reader.setDaemon(true);
            reader.start();
            final Thread writer = new Thread(new SocketWriter(stdIn, socketOut));
            writer.setDaemon(true);
            writer.start();

            while (true) {
                ;
            }

        } catch (final UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (final IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}

