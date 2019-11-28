package com.griddynamics.cloud.learning.dao.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "article")
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    private String caption;

    private String description;

    private String content;

    private Double price;

    @Column(name = "author_id")
    private Long authorId;

    private String currency;

    private LocalDateTime date;

    @Column(name = "free")
    private Boolean isFree;
}
