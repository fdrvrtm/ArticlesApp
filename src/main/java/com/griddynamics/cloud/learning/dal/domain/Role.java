package com.griddynamics.cloud.learning.dal.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Role.ROLE_TABLE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Role extends DomainEntity {

    public static final String ROLE_TABLE = "role";

    @Column(name = "description")
    private String description;

    public Role(long id, String description) {
        super(id);
        this.description = description;
    }
}
