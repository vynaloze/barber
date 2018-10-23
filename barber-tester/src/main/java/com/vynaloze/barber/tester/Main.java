package com.vynaloze.barber.tester;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private final static int CLIENTS = 3;
    private static final long DURATION = 180_000;

    public static void main(String[] args) throws Exception {
        final List<Tester> testers = new ArrayList<>();
        for (int i = 0; i < CLIENTS; i++) {
            final Tester tester = new Tester();
            testers.add(tester);
            final Thread thread = new Thread(tester);
            thread.start();
        }

        final long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < DURATION) {
            System.out.println("\033[H\033[2J");
            final int maxLength = testers.stream().map(t -> t.getLines().length).max(Integer::compareTo).get();
            for (int i = maxLength - 1; i >= 0; i--) {
                for (final Tester tester : testers) {
                    final String[] lines = tester.getLines();
                    if (i < lines.length) {
                        System.out.printf("%-60.60s ", lines[i]);
                    } else {
                        System.out.printf("%-60.60s ", "");
                    }
                }
                System.out.println();
            }
            Thread.sleep(2_000);
        }
        System.exit(0);
    }
}
