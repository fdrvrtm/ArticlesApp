package com.griddynamics.cloud.learning.dao;

public class DbException extends RuntimeException {

    public DbException() {
        super();
    }

    public DbException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}