package com.vynaloze.timebank.ws.pojo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class Service {
    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/MM/yyyy");

    private final int id;
    @Nonnull
    private final String contractor;
    @Nonnull
    private final LocalDate date;
    @Nonnull
    private final String title;
    @Nonnull
    private final String details;
    @Nullable
    private String principal;

    public Service(@Nonnull final String contractor, @Nonnull final LocalDate date, @Nonnull final String title, @Nonnull final String details) {
        this.id = COUNTER.getAndIncrement();
        this.contractor = contractor;
        this.date = date;
        this.title = title;
        this.details = details;
    }

    public Service(@Nonnull final Service service) {
        this.id = service.getId();
        this.contractor = service.getContractor();
        this.date = service.getDate();
        this.title = service.getTitle();
        this.details = service.getDetails();
        this.principal = service.getPrincipal();
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public String getContractor() {
        return contractor;
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

    @Nullable
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(@Nullable final String principal) {
        this.principal = principal;
    }

    public static DateTimeFormatter getDateFormatter() {
        return DATE_FORMATTER;
    }

    @Override
    public String toString() {
        return id + ";" + contractor + ";" + date.format(DATE_FORMATTER) + ";" + title + ";" + details + (principal == null ? "" : ";" + principal);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }

        final Service service = (Service) o;

        if (id != service.id) {
            return false;
        }
        if (!contractor.equals(service.contractor)) {
            return false;
        }
        if (!date.equals(service.date)) {
            return false;
        }
        if (!title.equals(service.title)) {
            return false;
        }
        if (!details.equals(service.details)) {
            return false;
        }
        return principal != null ? principal.equals(service.principal) : service.principal == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + contractor.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + details.hashCode();
        result = 31 * result + (principal != null ? principal.hashCode() : 0);
        return result;
    }

}