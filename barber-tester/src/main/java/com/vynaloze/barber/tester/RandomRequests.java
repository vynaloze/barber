package com.vynaloze.barber.tester;

import java.util.Random;

public class RandomRequests {
    private static Random random = new Random();

    public static String random() {
        switch (random.nextInt(3)) {
            case 0:
                return randomCancel();
            case 1:
                return randomReserve();
            case 2:
                return randomGet();
        }
        return randomGet();
    }

    public static String randomGet() {
        return "GET;";
    }

    public static String randomReserve() {
        return "RESERVE;" + (random.nextInt(9) + 10) + ";client" + random.nextInt(4);
    }

    public static String randomCancel() {
        return "CANCEL;" + (random.nextInt(9) + 10);
    }
}
