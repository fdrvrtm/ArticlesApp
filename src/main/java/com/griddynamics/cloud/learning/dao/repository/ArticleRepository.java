package com.griddynamics.cloud.learning.dao.repository;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.PaginatedResult;
import com.griddynamics.cloud.learning.web.dto.PaginatedRequestParams;

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

    public PaginatedResult<Article> getArticles(final PaginatedRequestParams params) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseType(final PaginatedRequestParams params, final Boolean isFree) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article WHERE free = " + isFree;
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByTag(final PaginatedRequestParams params, final String tag) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + tag + "\'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseTypeAndTag(final PaginatedRequestParams params, final String tag, final Boolean isFree) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + tag + "\' AND free = " + isFree;
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByCaption(final PaginatedRequestParams params, final String caption) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article WHERE caption LIKE '%" + caption + "%'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseTypeAndCaption(final PaginatedRequestParams params, final Boolean free, final String caption) {
        String querySelector = "SELECT *";
        String queryCondition = "FROM article WHERE free = " + free + " AND caption LIKE '%"
                + caption + "%'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByTagAndCaption(final PaginatedRequestParams params, final String tag, final String caption) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + tag + "\' AND caption LIKE '%" + caption + "%'";
        return getPaginatedEntities(querySelector, queryCondition, params);
    }

    public PaginatedResult<Article> getArticlesByPurchaseTypeAndTagAndCaption(final PaginatedRequestParams params, final String tag, final String caption, final Boolean isFree) {
        String querySelector = "SELECT a.id, a.caption, a.description, a.content, a.price, a.author_id, a.currency, a.date, a.free";
        String queryCondition = "FROM article AS a INNER JOIN tag_article AS ta ON a.id = ta.article_id "
                + "INNER JOIN tag AS t ON ta.tag_id = t.id "
                + "WHERE t.name = \'" + tag + "\' AND caption LIKE '%" + caption
                + "%' AND free = " + isFree;
        return getPaginatedEntities(querySelector, queryCondition, params);
    }
}