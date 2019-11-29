package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.repository.ArticleRepository;
import com.griddynamics.cloud.learning.web.dto.ArticleDto;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ArticleService {

    private ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Page<ArticleDto> findArticles(ArticleFilterRequest request, Pageable pageable){
        return convertArticles(repository.findAll(pageable));
    }

    private Page<ArticleDto> convertArticles(Page<Article> articles) {
        return new PageImpl<ArticleDto>(Collections.emptyList());
    }
}
