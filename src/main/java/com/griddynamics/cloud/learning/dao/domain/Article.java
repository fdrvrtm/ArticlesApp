package com.griddynamics.cloud.learning.dao.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "article")
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tag_article",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();
}
