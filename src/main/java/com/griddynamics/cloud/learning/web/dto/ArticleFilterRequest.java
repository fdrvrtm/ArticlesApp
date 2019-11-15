package com.griddynamics.cloud.learning.web.dto;

import lombok.Data;

@Data
public class ArticleFilterRequest {

    private final PaginatedRequestParams params;
    private final PurchaseType purchaseType;
    private final String caption;
    private final String tag;
}