package com.vynaloze.timebank.common;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Service {
    @Nonnull
    private final User user;
    @Nonnull
    private final LocalDate date;
    @Nonnull
    private final String title;
    @Nonnull
    private final String details;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");

    public Service(@Nonnull final User user, @Nonnull final LocalDate date, @Nonnull final String title, @Nonnull final String details) {
        this.user = user;
        this.date = date;
        this.title = title;
        this.details = details;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    @Nonnull
    public LocalDate getDate() {
        return date;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    @Nonnull
    public String getDetails() {
        return details;
    }

    public static DateTimeFormatter getDateFormatter() {
        return DATE_FORMATTER;
    }

    @Override
    public String toString() {
        return user + ";" + date.format(DATE_FORMATTER) + ";" + title + ";" + details;
    }
}

