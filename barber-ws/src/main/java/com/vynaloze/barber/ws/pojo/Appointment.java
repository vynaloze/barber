package com.vynaloze.barber.ws.pojo;

import java.util.Optional;

public class Appointment {
    private final int hour;
    private String client;
    public static final int OPEN_HOUR = 10;
    public static final int CLOSE_HOUR = 18;

    public Appointment(int hour) {
        this.hour = hour;
        this.client = null;
    }

    public int getHour() {
        return hour;
    }

    public Optional<String> getClient() {
        return Optional.ofNullable(client);
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment)) {
            return false;
        }

        Appointment that = (Appointment) o;

        if (hour != that.hour) {
            return false;
        }
        return client != null ? client.equals(that.client) : that.client == null;
    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "hour=" + hour +
                ", client=" + client +
                '}';
    }
}
