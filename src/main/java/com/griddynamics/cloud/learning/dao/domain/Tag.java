package com.griddynamics.cloud.learning.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
