package com.griddynamics.cloud.learning.dao.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
@Table(name = "user_article")
public class UserArticle {

    @EmbeddedId
    private UserArticlePK userArticlePK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "articleId")
    private Article article;

    private Boolean owned;

    private Boolean liked;

    public UserArticle(User user, Article article, Boolean liked) {
        this(new UserArticlePK(user.getId(), article.getId()), user, article, Boolean.FALSE, liked);
    }
}
