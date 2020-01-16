package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.SpringUserImpl;
import com.griddynamics.cloud.learning.dao.repository.ArticleRepository;
import com.griddynamics.cloud.learning.dao.repository.UserArticleRepository;
import com.griddynamics.cloud.learning.web.dto.ArticleDto;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;
import com.griddynamics.cloud.learning.web.dto.ArticleLikeDto;
import com.griddynamics.cloud.learning.web.dto.PurchaseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    private ArticleMapper articleMapper;

    private UserArticleRepository userArticleRepository;

    public ArticleService(ArticleRepository articleRepository, ArticleMapper articleMapper, UserArticleRepository userArticleRepository) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.userArticleRepository = userArticleRepository;
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
            articles = articleRepository.findAll(pageable);
        } else if (!StringUtils.isEmpty(tag) && !StringUtils.isEmpty(caption)) {
            articles = articleRepository.findAllByTagAndCaption(tag, caption, pageable);
        } else if (!StringUtils.isEmpty(tag)) {
            articles = articleRepository.findAllByTag(tag, pageable);
        } else {
            articles = articleRepository.findAllByCaptionContaining(caption, pageable);
        }
        return convertPage(articles);
    }

    private Page<ArticleDto> getArticlesByPurchaseType(Boolean isFree, String tag, String caption, Pageable pageable) {

        Page<Article> articles;

        if (StringUtils.isEmpty(tag) && StringUtils.isEmpty(caption)) {
            articles = articleRepository.findAllByIsFree(isFree, pageable);
        } else if (!StringUtils.isEmpty(tag) && !StringUtils.isEmpty(caption)) {
            articles = articleRepository.findAllByIsFreeAndTagAndCaption(isFree, tag, caption, pageable);
        } else if (!StringUtils.isEmpty(tag)) {
            articles = articleRepository.findAllByIsFreeAndTag(isFree, tag, pageable);
        } else {
            articles = articleRepository.findAllByIsFreeAndCaptionContaining(isFree, caption, pageable);
        }
        return convertPage(articles);
    }

    private Page<ArticleDto> convertPage(Page<Article> articles) {
        return new PageImpl<>(articles.getContent().stream()
                .map(articleMapper::articleToArticleDto)
                .collect(Collectors.toList()), articles.getPageable(), articles.getTotalElements());
    }

    @Transactional
    public ArticleLikeDto likeAnArticle(final ArticleLikeDto likeDto) {

        final Long currentUserId = getCurrentUserId();
        final Long currentArticleId = likeDto.getArticleId();
        final Boolean liked = likeDto.getLiked();

        userArticleRepository.getByUserIdAndArticleId(currentUserId, currentArticleId)
                .ifPresentOrElse(ua -> ua.setLiked(liked),
                () -> createUserArticle(currentUserId, currentArticleId, liked));

        return likeDto;
    }

    private void createUserArticle(final Long userId, final Long articleId, final Boolean liked) {
        userArticleRepository.createNew(userId, articleId, liked);
    }

    private Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUserImpl currentUser = (SpringUserImpl) authentication.getPrincipal();
        return currentUser.getId();
    }
}
