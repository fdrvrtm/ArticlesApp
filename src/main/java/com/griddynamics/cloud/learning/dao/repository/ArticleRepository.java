package com.griddynamics.cloud.learning.dao.repository;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.PaginatedResult;
import com.griddynamics.cloud.learning.web.dto.ArticlePaginatedRequestParams;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Logger;

public class ArticleRepository extends Repository<Article> {

    private static final Logger logger = Logger.getLogger(ArticleRepository.class.getName());

    @Override
    protected Optional<Article> convertToEntity(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String caption = resultSet.getString("caption");
            String description = resultSet.getString("description");
            String content = resultSet.getString("content");
            Double price = resultSet.getDouble("price");
            Long author_id = resultSet.getLong("author_id");
            String currency = resultSet.getString("currency");
            Timestamp date = resultSet.getTimestamp("date");
            Boolean isFree = resultSet.getBoolean("free");

            return Optional.of(
                    Article.builder()
                            .id(id)
                            .caption(caption)
                            .description(description)
                            .content(content)
                            .price(price)
                            .authorId(author_id)
                            .currency(currency)
                            .date(date.toLocalDateTime())
                            .isFree(isFree)
                            .build());
        } catch (SQLException ex) {
            throw generateException(ex, "Unable to convert a result set to an entity.");
        }
    }

    @Override
    protected String getEntityTableName() {
        return Article.ARTICLE_TABLE;
    }

    public PaginatedResult<Article> getArticles(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseType(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article WHERE free = " + params.getIsFree();
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByTag(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + params.getTag() + "\'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseTypeAndTag(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + params.getTag() + "\' AND free = " + params.getIsFree();
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByCaption(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article WHERE caption LIKE '%" + params.getCaption() + "%'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseTypeAndCaption(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article WHERE free = " + params.getIsFree() + " AND caption LIKE '%"
                + params.getCaption() + "%'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByTagAndCaption(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + params.getTag() + "\' AND caption LIKE '%" + params.getCaption() + "%'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseTypeAndTagAndCaption(final ArticlePaginatedRequestParams params) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + params.getTag() + "\' AND caption LIKE '%" + params.getCaption()
                + "%' AND free = " + params.getIsFree();
        return getPaginatedEntities(querySelector, queryCondition, params);
    }
}