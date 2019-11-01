package com.griddynamics.cloud.learning.dal.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = User.USER_TABLE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends DomainEntity {

    public static final String USER_TABLE = "user";

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public User(long id, String username) {
        super(id);
        this.username = username;
    }
}
