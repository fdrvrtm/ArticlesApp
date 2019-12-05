package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.repository.ArticleRepository;
import com.griddynamics.cloud.learning.web.dto.ArticleDto;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;
import com.griddynamics.cloud.learning.web.dto.PurchaseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Service
public class ArticleService {

    private ArticleRepository repository;

    private ArticleMapper articleMapper;

    public ArticleService(ArticleRepository repository, ArticleMapper articleMapper) {
        this.repository = repository;
        this.articleMapper = articleMapper;
    }

    public Page<ArticleDto> findArticles(ArticleFilterRequest request, Pageable pageable) {

        Page<ArticleDto> articles;
        final PurchaseType purchaseType = request.getType();
        final String tag = request.getTag();
        final String caption = request.getCaption();

        if (purchaseType != PurchaseType.ALL) {
            articles = getArticlesByPurchaseType(purchaseType == PurchaseType.FREE, tag, caption, pageable);
        } else {
            articles = getArticles(tag, caption, pageable);
        }
        return articles;
    }

    private Page<ArticleDto> getArticles(String tag, String caption, Pageable pageable) {

        Page<Article> articles;

        if (StringUtils.isEmpty(tag) && StringUtils.isEmpty(caption)) {
            articles = repository.findAll(pageable);
        } else if (!StringUtils.isEmpty(tag) && !StringUtils.isEmpty(caption)) {
            articles = repository.findAllByTagAndCaption(tag, caption, pageable);
        } else if (!StringUtils.isEmpty(tag)) {
            articles = repository.findAllByTag(tag, pageable);
        } else {
            articles = repository.findAllByCaptionContaining(caption, pageable);
        }
        return convertPage(articles);
    }

    private Page<ArticleDto> getArticlesByPurchaseType(Boolean isFree, String tag, String caption, Pageable pageable) {

        Page<Article> articles;

        if (StringUtils.isEmpty(tag) && StringUtils.isEmpty(caption)) {
            articles = repository.findAllByIsFree(isFree, pageable);
        } else if (!StringUtils.isEmpty(tag) && !StringUtils.isEmpty(caption)) {
            articles = repository.findAllByIsFreeAndTagAndCaption(isFree, tag, caption, pageable);
        } else if (!StringUtils.isEmpty(tag)) {
            articles = repository.findAllByIsFreeAndTag(isFree, tag, pageable);
        } else {
            articles = repository.findAllByIsFreeAndCaptionContaining(isFree, caption, pageable);
        }
        return convertPage(articles);
    }

    private Page<ArticleDto> convertPage(Page<Article> articles) {
        return new PageImpl<>(articles.getContent().stream()
                .map(articleMapper::articleToArticleDto)
                .collect(Collectors.toList()), articles.getPageable(), articles.getTotalElements());
    }
}
