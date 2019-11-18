package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.PaginatedResult;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;

import java.util.Collections;

public class ArticleService {

    //TODO (Just a stub for now; part of service implementation task)
    public PaginatedResult<Article> getArticles(ArticleFilterRequest requestFilter) {

        return new PaginatedResult<>(Collections.emptyList(), 0);
    }
}