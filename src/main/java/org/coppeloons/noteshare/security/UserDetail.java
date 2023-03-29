package org.coppeloons.noteshare.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetail extends org.springframework.security.core.userdetails.User {
    Long id;

    public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
