package com.griddynamics.cloud.learning.dao.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Article extends DomainEntity {

    public static final String ARTICLE_TABLE = "article";

    @Builder
    public Article(Long id, String caption, String description, String content, Double price,
                   Long authorId, String currency, LocalDateTime date, Boolean isFree) {
        super(id);
        this.caption = caption;
        this.description = description;
        this.content = content;
        this.price = price;
        this.authorId = authorId;
        this.currency = currency;
        this.date = date;
        this.isFree = isFree;
    }

    private String caption;

    private String description;

    private String content;

    private Double price;

    private final Long authorId;

    private String currency;

    private final LocalDateTime date;

    private Boolean isFree;
}