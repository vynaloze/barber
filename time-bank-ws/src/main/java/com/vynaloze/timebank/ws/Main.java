package com.vynaloze.timebank.ws;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        int portNumber = 8080;
        boolean listening = true;
        List<Connection> connections = new ArrayList<>();
        Dao dao = new DaoImpl();
        Controller controller = new Controller(dao);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                connections.add(new Connection(serverSocket.accept(), controller));
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
