package com.griddynamics.cloud.learning.dao.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SpringUserImpl extends User {

    private final Long id;

    private String email;

    public SpringUserImpl(Long id, String username, String email, String password,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
    }
}
