package com.griddynamics.cloud.learning.web;

import com.griddynamics.cloud.learning.service.ArticleService;
import com.griddynamics.cloud.learning.web.dto.ArticleDto;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class ArticleController {

    private ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping(value = "/articles")
    public Page<ArticleDto> getArticles(@Valid ArticleFilterRequest request, Pageable pageable) {
        return service.findArticles(request, pageable);
    }
}