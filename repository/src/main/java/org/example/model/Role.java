package org.example.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT, EMPLOYEE, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
