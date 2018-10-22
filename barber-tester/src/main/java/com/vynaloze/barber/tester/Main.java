package com.vynaloze.barber.tester;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static int CLIENTS = 2;
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        List<Tester> testers = new ArrayList<>();
        for (int i = 0; i < CLIENTS; i++) {
            testers.add(new Tester());
        }

        while (true) {

            System.out.println(testers.get(0).foo());
            Thread.sleep(1000);
        }
    }
}
