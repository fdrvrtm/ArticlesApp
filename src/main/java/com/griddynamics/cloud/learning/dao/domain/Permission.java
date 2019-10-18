package com.griddynamics.cloud.learning.dao.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Permission extends DomainEntity {

    public static final String PERMISSION_TABLE = "permission";

    private String name;

    public Permission(Long id, String name) {
        super(id);
        this.name = name;
    }
}