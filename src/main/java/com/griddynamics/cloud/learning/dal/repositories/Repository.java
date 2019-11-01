package com.griddynamics.cloud.learning.dal.repositories;

import com.griddynamics.cloud.learning.dal.connection.DBConnectionManager;
import com.griddynamics.cloud.learning.dal.domain.DomainEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public abstract class Repository<T extends DomainEntity> {

    protected abstract String getEntityTableName();

    protected abstract T convertToEntity(ResultSet resultSet);

    public abstract void add(T item);

    public abstract void update(T item, String columnName, String newValue);

    public void delete(T item) {

        String query = "DELETE FROM " + getEntityTableName() + " WHERE id = " + item.getId() + ";";

        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(Repository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public Optional<T> getById(long id) {

        String query = "SELECT * FROM " + getEntityTableName() + " WHERE id = " + id + ";";

        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            T entity = convertToEntity(resultSet);

            return Optional.of(entity);

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(Repository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional<List<T>> getAll() {

        String query = "SELECT * FROM " + getEntityTableName() + ";";

        try (Connection connection = DBConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = convertToEntity(resultSet);
                entities.add(entity);
            }

            return Optional.of(entities);

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(Repository.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.empty();
    }
}
