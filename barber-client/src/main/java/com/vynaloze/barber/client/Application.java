package com.vynaloze.barber.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Application {

    public static void main(final String[] args) {
        final String hostName = "localhost";
        final int portNumber = 8080;

        final Client client = new Client(new PrintWriter(System.out, true));
        client.connect(hostName, portNumber);

        String input;
        try (final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            while ((input = stdIn.readLine()) != null) {
                client.send(input);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}

