package com.griddynamics.cloud.learning.dao.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tag extends DomainEntity {

    public static final String TAG_TABLE = "tag";

    private String name;

    public Tag(Long id, String name) {
        super(id);
        this.name = name;
    }
}