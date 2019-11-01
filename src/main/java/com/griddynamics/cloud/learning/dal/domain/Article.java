package com.griddynamics.cloud.learning.dal.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = Article.ARTICLE_TABLE)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Article extends DomainEntity {

    public static final String ARTICLE_TABLE = "article";

    @Builder
    public Article(long id, String caption, String description, String content, double price,
                   long authorId, String currency, LocalDateTime date) {
        super(id);
        this.caption = caption;
        this.description = description;
        this.content = content;
        this.price = price;
        this.authorId = authorId;
        this.currency = currency;
        this.date = date;
    }

    @Column(name = "caption")
    private String caption;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "price")
    private double price;

    @Column(name = "author_id")
    private long authorId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "date")
    private LocalDateTime date;
}

