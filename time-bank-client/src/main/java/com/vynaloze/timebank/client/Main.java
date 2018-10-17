package com.vynaloze.timebank.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 8080;

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter stdOut = new PrintWriter(System.out, true);

            Thread t1 = new Thread(new SocketReader(in, stdOut));
            t1.setDaemon(true);
            t1.start();
            Thread t2 = new Thread(new SocketWriter(stdIn, out));
            t2.setDaemon(true);
            t2.start();

            while(true);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}

