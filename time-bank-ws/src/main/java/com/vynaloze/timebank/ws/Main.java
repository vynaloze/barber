package com.vynaloze.timebank.ws;

import com.vynaloze.timebank.ws.controller.Broadcaster;
import com.vynaloze.timebank.ws.controller.Connection;
import com.vynaloze.timebank.ws.controller.Controller;
import com.vynaloze.timebank.ws.dao.Dao;
import com.vynaloze.timebank.ws.dao.DaoImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(final String[] args) throws Exception {
        final int portNumber = 8080;
        final List<Connection> connections = new ArrayList<>();
        final Dao dao = new DaoImpl();
        final Controller controller = new Controller(dao);
        final Broadcaster broadcaster = new Broadcaster();

        try (final ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                connections.add(new Connection(serverSocket.accept(), controller, broadcaster));
            }
        } catch (final IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
