package com.griddynamics.cloud.learning.dal.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Tag.TAG_TABLE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Tag extends DomainEntity {

    public static final String TAG_TABLE = "tag";

    @Column(name = "name")
    private String name;

    public Tag(long id, String name) {
        super(id);
        this.name = name;
    }
}
