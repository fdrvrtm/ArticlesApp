package com.griddynamics.cloud.learning.dao.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class UserArticlePK implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "article_id")
    private Long articleId;
}
