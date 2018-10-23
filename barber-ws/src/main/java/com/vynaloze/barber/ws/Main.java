package com.vynaloze.barber.ws;

import com.vynaloze.barber.ws.controller.Broadcaster;
import com.vynaloze.barber.ws.controller.Connection;
import com.vynaloze.barber.ws.controller.Controller;
import com.vynaloze.barber.ws.dao.Dao;
import com.vynaloze.barber.ws.dao.DaoImpl;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static int portNumber = 8080;
    private final static List<Connection> connections = new ArrayList<>();
    private final static Dao dao = new DaoImpl();
    private final static Controller controller = new Controller(dao, 0);
    private final static Controller retardedController = new Controller(dao, 30_000);
    private final static Broadcaster broadcaster = new Broadcaster();

    public static void main(final String[] args) throws Exception {
        int counter = 0;

        try (final ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                if (counter % 3 == 0) {
                    connections.add(new Connection(serverSocket.accept(), controller, broadcaster));
                } else {
                    connections.add(new Connection(serverSocket.accept(), retardedController, broadcaster));
                }
                counter++;
            }
        } catch (final IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
