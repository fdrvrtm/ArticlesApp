package com.griddynamics.cloud.learning.dal.repositories;

import com.griddynamics.cloud.learning.dal.connection.DBConnectionManager;
import com.griddynamics.cloud.learning.dal.domain.Article;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleRepository extends Repository<Article> {

    @Override
    protected Article convertToEntity(ResultSet resultSet) {
        try {
            long id = resultSet.getLong("id");
            String caption = resultSet.getString("caption");
            String description = resultSet.getString("description");
            String content = resultSet.getString("content");
            double price = resultSet.getDouble("price");
            long author_id = resultSet.getLong("author_id");
            String currency = resultSet.getString("currency");
            Timestamp date = resultSet.getTimestamp("date");

            return Article.builder()
                    .id(id)
                    .caption(caption)
                    .description(description)
                    .content(content)
                    .price(price)
                    .authorId(author_id)
                    .currency(currency)
                    .date(date.toLocalDateTime())
                    .build();

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(ArticleRepository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected String getEntityTableName() {
        return Article.ARTICLE_TABLE;
    }

    @Override
    public void add(Article item) {
        String query = "INSERT INTO " + getEntityTableName() + " VALUES(?,?,?,?,?,?,?,?);";

        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, item.getId());
            statement.setString(2, item.getCaption());
            statement.setString(3, item.getDescription());
            statement.setString(4, item.getContent());
            statement.setDouble(5, item.getPrice());
            statement.setLong(6, item.getAuthorId());
            statement.setString(7, item.getCurrency());
            statement.setTimestamp(8, Timestamp.valueOf(item.getDate()));

            statement.executeUpdate();

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(ArticleRepository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    @Override
    public void update(Article item, String columnName, String newValue) {

        String query = "UPDATE " + getEntityTableName() + " SET " + columnName + " = \'" + newValue +
                "\' WHERE id = " + item.getId() + ";";

        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(ArticleRepository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public Optional<List<Article>> getFree() {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE price = 0.0;";

        return getArticles(query);
    }

    public Optional<List<Article>> getPaid() {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE NOT price = 0.0;";

        return getArticles(query);
    }

    public Optional<List<Article>> getArticlesByAuthor(long author_id) {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE author_id = " + author_id + ";";

        return getArticles(query);
    }

    public Optional<List<Article>> getArticlesByCurrency(String currency) {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE currency = \'" + currency + "\';";

        return getArticles(query);
    }

    public Optional<List<Article>> getBySpecifiedDate(LocalDateTime date) {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE date = \'" + date.toLocalDate() + "\';";

        return getArticles(query);
    }

    public Optional<List<Article>> getAfterSpecifiedDate(LocalDateTime date) {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE date > \'" + date.toLocalDate() + "\';";

        return getArticles(query);
    }

    public Optional<Article> findByTitle(String title) {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE caption = \'" + title + "\';";

        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Article article = convertToEntity(resultSet);

            return Optional.ofNullable(article);

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(ArticleRepository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.empty();
    }

    private Optional<List<Article>> getArticles(String query) {

        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Article> articles = new ArrayList<>();
            while (resultSet.next()) {
                Article entity = convertToEntity(resultSet);
                articles.add(entity);
            }

            return Optional.of(articles);

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(ArticleRepository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.empty();
    }
}