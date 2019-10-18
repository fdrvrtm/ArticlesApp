package com.griddynamics.cloud.learning.dao.repository;

import com.griddynamics.cloud.learning.dao.DbException;
import com.griddynamics.cloud.learning.dao.connection.DBConnectionManager;
import com.griddynamics.cloud.learning.dao.domain.DomainEntity;
import com.griddynamics.cloud.learning.dao.domain.PaginatedResult;
import com.griddynamics.cloud.learning.web.dto.PaginatedRequestParams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Repository<T extends DomainEntity> {

    private static final Logger logger = Logger.getLogger(Repository.class.getName());

    protected abstract String getEntityTableName();

    protected abstract Optional<T> convertToEntity(ResultSet resultSet);

    public Optional<T> getById(final long id) {
        String query = "SELECT * FROM " + getEntityTableName() + " WHERE id = " + id + ";";
        return getEntity(query);
    }

    protected Optional<T> getEntity(final String query) {
        try (Connection connection = DBConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return convertToEntity(resultSet);
        } catch (SQLException ex) {
            throw generateException(ex, "Unable to retrieve required entity. Query \"" + query + "\" aborted.");
        }
    }

    protected PaginatedResult<T> getPaginatedEntities(final String querySelector, final String queryCondition, final PaginatedRequestParams params) {
        final Integer startRow = (params.getPageNumber() - 1) * params.getLimitPerPage();
        final String countQuery = "SELECT COUNT(*) " + queryCondition + ";";
        final String paginatedOrderedQuery = querySelector + " " + queryCondition
                + " ORDER BY " + params.getOrderBy() + " " + params.getSortDirection()
                + " LIMIT " + params.getLimitPerPage() + " OFFSET " + startRow + ";";

        try (Connection connection = DBConnectionManager.getConnection()) {

            final Integer rowsCount = countRows(connection, countQuery);
            final List<T> entities = getEntities(connection, paginatedOrderedQuery);

            return new PaginatedResult<>(entities, rowsCount);
        } catch (SQLException ex) {
            throw generateException(ex, "Something bad happened during connection establishment.");
        }
    }

    protected DbException generateException(final Throwable ex, final String message) {
        logger.log(Level.SEVERE, message, ex);
        return new DbException(message, ex);
    }

    private List<T> getEntities(final Connection connection, final String query) {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                Optional<T> entity = convertToEntity(resultSet);
                entity.ifPresent(entities::add);
            }
            return entities;
        } catch (SQLException ex) {
            throw generateException(ex, "Unable to retrieve required entities. Query \"" + query + "\" aborted.");
        }
    }

    private int countRows(final Connection connection, final String countQuery) {
        try {
            PreparedStatement statement = connection.prepareStatement(countQuery);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException ex) {
            throw generateException(ex, "Unable to count rows. Query execution aborted. Query \"" + countQuery + "\" aborted.");
        }
    }
}