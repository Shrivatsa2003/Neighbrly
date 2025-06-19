package com.projects.Neighbrly.Neighbrly.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    GUEST,
    HOTEL_MANAGER;

    @Override
    public String getAuthority() {
        return toString();
    }
}

