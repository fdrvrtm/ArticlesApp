package com.griddynamics.cloud.learning.dao.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Role extends DomainEntity {

    public static final String ROLE_TABLE = "role";

    private String description;

    public Role(Long id, String description) {
        super(id);
        this.description = description;
    }
}