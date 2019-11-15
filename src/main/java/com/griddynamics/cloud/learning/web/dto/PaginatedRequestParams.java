package com.griddynamics.cloud.learning.web.dto;

import lombok.Data;

@Data
public class PaginatedRequestParams {

    protected final SortColumn orderBy;
    protected final SortDirection sortDirection;

    protected final Integer pageNumber;
    protected final Integer limitPerPage;
}