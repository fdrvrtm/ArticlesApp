package com.griddynamics.cloud.learning.dal.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Permission.PERMISSION_TABLE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Permission extends DomainEntity {

    public static final String PERMISSION_TABLE = "permission";

    @Column(name = "name")
    private String name;

    public Permission(long id, String name) {
        super(id);
        this.name = name;
    }
}
