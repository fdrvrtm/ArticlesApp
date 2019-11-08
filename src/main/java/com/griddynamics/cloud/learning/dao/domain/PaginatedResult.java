package com.griddynamics.cloud.learning.dao.domain;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResult<T extends DomainEntity> {

    private final List<T> entities;
    private final Integer rowsCount;
}
