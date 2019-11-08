package com.griddynamics.cloud.learning.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class ArticlePaginatedRequestParams extends PaginatedRequestParams {

    private final Boolean isFree;
    private final String caption;
    private final String tag;
}