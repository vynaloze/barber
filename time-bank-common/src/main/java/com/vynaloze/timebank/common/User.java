package com.vynaloze.timebank.common;

import javax.annotation.Nonnull;

public class User {
    @Nonnull
    private final String name;
    @Nonnull
    private final Role role;

    public User(@Nonnull final String name, @Nonnull final Role role) {
        this.name = name;
        this.role = role;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return name + ";" + role;
    }

    public enum Role {
        PRINCIPAL, CONTRACTOR;

        public static Role getRole(String string) {
            for (Role role : Role.values()) {
                if (role.toString().contains(string.toUpperCase())) {
                    return role;
                }
            }
            throw new RuntimeException("Invalid string - role not found");
        }
    }
}
