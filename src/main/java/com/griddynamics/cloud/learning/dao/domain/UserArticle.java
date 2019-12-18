package com.griddynamics.cloud.learning.dao.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_article")
public class UserArticle implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private Boolean owned;

    private Boolean liked;

    @Override
    public int hashCode() {
        return Objects.hash(user.getId(), article.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (!(obj instanceof UserArticle)) { return false; }

        UserArticle that = (UserArticle) obj;
        return Objects.equals(this.user.getId(), that.user.getId())
                && Objects.equals(this.article.getId(), that.article.getId());
    }
}
