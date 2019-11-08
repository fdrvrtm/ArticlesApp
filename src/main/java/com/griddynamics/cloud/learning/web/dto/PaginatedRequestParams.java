package com.griddynamics.cloud.learning.web.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PaginatedRequestParams {

    protected final String orderBy;
    protected final SortDirection sortDirection;

    protected final Integer pageNumber;
    protected final Integer limitPerPage;
}