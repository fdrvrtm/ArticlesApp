package com.griddynamics.cloud.learning.dao.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "common_id_generator")
    @SequenceGenerator(name = "common_id_generator", sequenceName = "common_id_seq", allocationSize = 1)
    private Long id;

    private String caption;

    private String description;

    private String content;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User author;

    private String currency;

    private LocalDateTime date;

    @Column(name = "free")
    private Boolean isFree;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tag_article",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private Set<UserArticle> userArticles = new HashSet<>();
}
