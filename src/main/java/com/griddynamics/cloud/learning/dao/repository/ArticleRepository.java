package com.griddynamics.cloud.learning.dao.repository;

import com.griddynamics.cloud.learning.dao.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

    Page<Article> findAllByIsFree(Boolean isFree, Pageable pageable);

    Page<Article> findAllByCaptionContaining(String caption, Pageable pageable);

    Page<Article> findAllByIsFreeAndCaptionContaining(Boolean isFree, String caption, Pageable pageable);

    @Query(value = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free "
            + "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id INNER JOIN tag AS t ON ta.tag_id = t.id "
            + "WHERE t.name = :tagName", nativeQuery = true)
    Page<Article> findAllByTag(@Param("tagName") String tag, Pageable pageable);

    @Query(value = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free "
            + "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id INNER JOIN tag AS t ON ta.tag_id = t.id "
            + "WHERE a.free = :isFree AND t.name = :tagName", nativeQuery = true)
    Page<Article> findAllByIsFreeAndTag(@Param("isFree") Boolean isFree, @Param("tagName") String tag, Pageable pageable);

    @Query(value = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free "
            + "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id INNER JOIN tag AS t ON ta.tag_id = t.id "
            + "WHERE t.name = :tagName AND a.caption LIKE %:caption%", nativeQuery = true)
    Page<Article> findAllByTagAndCaption(@Param("tagName") String tag, @Param("caption") String caption, Pageable pageable);

    @Query(value = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free "
            + "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id INNER JOIN tag AS t ON ta.tag_id = t.id "
            + "WHERE a.free = :isFree AND t.name = :tagName AND a.caption LIKE %:caption%", nativeQuery = true)
    Page<Article> findAllByIsFreeAndTagAndCaption(@Param("isFree") Boolean isFree, @Param("tagName") String tag,
                                                  @Param("caption") String caption, Pageable pageable);
}
