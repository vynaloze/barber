package com.vynaloze.timebank.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Request {
    @Nonnull
    private final Type type;
    @Nullable
    private final Service service;

    public Request(@Nonnull final Type type, @Nullable final Service service) {
        this.type = type;
        this.service = service;
    }

    public Request(@Nonnull final Type type) {
        this.type = type;
        this.service = null;
    }

    @Nonnull
    public Type getType() {
        return type;
    }

    @Nullable
    public Service getService() {
        return service;
    }

    public enum Type {
        GET, POST, DELETE, DROP, INVALID
    }
}
