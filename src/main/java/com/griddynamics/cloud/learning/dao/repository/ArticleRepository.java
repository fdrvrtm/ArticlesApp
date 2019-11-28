package com.griddynamics.cloud.learning.dao.repository;

import com.griddynamics.cloud.learning.dao.domain.Article;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
}
