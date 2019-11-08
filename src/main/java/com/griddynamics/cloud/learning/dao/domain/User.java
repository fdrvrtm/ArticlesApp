package com.griddynamics.cloud.learning.dao.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends DomainEntity {

    public static final String USER_TABLE = "user";

    private final String username;

    private String email;

    private String password;

    public User(Long id, String username) {
        super(id);
        this.username = username;
    }
}