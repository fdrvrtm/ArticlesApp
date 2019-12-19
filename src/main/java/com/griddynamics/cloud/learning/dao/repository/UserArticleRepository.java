package com.griddynamics.cloud.learning.dao.repository;

import com.griddynamics.cloud.learning.dao.domain.UserArticle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserArticleRepository extends CrudRepository<UserArticle, Long> {

    Optional<UserArticle> getByUserIdAndArticleId(Long userId, Long articleId);

    @Modifying
    @Query(value = "INSERT INTO user_article (user_id, article_id, liked) VALUES (:userId, :articleId, :liked)", nativeQuery = true)
    void createNew(@Param("userId") Long userId, @Param("articleId") Long articleId, @Param("liked") Boolean liked);
}
