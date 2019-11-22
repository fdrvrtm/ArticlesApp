package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.PaginatedResult;
import com.griddynamics.cloud.learning.dao.repository.ArticleRepository;
import com.griddynamics.cloud.learning.web.dto.ArticleDto;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;
import com.griddynamics.cloud.learning.web.dto.PaginatedRequestParams;
import com.griddynamics.cloud.learning.web.dto.PurchaseType;

import java.util.stream.Collectors;

public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService() {
        this(new ArticleRepository());
    }

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets articles by given filter and sorting params.
     *
     * @param requestFilter - request dto, that contains sorting and filtering info.
     * @return - paginated list of articles.
     */

    public PaginatedResult<ArticleDto> findArticles(ArticleFilterRequest requestFilter) {

        PaginatedResult<ArticleDto> articles;

        final PurchaseType purchaseType = requestFilter.getPurchaseType();
        final String tag = requestFilter.getTag();
        final String caption = requestFilter.getCaption();
        final PaginatedRequestParams params = requestFilter.getParams();

        if (purchaseType != PurchaseType.ALL) {
            articles = getArticlesByPurchaseType(tag, caption, params, purchaseType == PurchaseType.FREE);
        } else {
            articles = getArticles(tag, caption, params);
        }
        return articles;
    }

    /**
     * Returns paginated articles by filters
     * Invoked when {@code PurchaseType.All} was specified by the user.
     *
     * @param tag     a string representation of tag filter
     * @param caption a string representation of caption filter
     * @param params  a dto for ordering and paginating request results
     * @return a PaginatedResult object
     */

    private PaginatedResult<ArticleDto> getArticles(String tag, String caption, PaginatedRequestParams params) {

        PaginatedResult<Article> articles;

        if (tag.isEmpty() && caption.isEmpty()) {
            articles = repository.getArticles(params);
        } else if (!caption.isEmpty() && !tag.isEmpty()) {
            articles = repository.getArticlesByTagAndCaption(params, tag, caption);
        } else if (!tag.isEmpty()) {
            articles = repository.getArticlesByTag(params, tag);
        } else {
            articles = repository.getArticlesByCaption(params, caption);
        }
        return convertArticles(articles);
    }

    /**
     * Returns paginated articles by filters
     * Invoked when filtering by purchase type is required.
     *
     * @param tag     a string representation of tag filter
     * @param caption a string representation of caption filter
     * @param params  a dto used for ordering and paginating request results
     * @param isFree  a boolean representation of purchase type
     * @return a PaginatedResult object
     */

    private PaginatedResult<ArticleDto> getArticlesByPurchaseType(String tag, String caption, PaginatedRequestParams params, Boolean isFree) {

        PaginatedResult<Article> articles;

        if (tag.isEmpty() && caption.isEmpty()) {
            articles = repository.getArticlesByPurchaseType(params, isFree);
        } else if (!caption.isEmpty() && !tag.isEmpty()) {
            articles = repository.getArticlesByPurchaseTypeAndTagAndCaption(params, isFree, tag, caption);
        } else if (!tag.isEmpty()) {
            articles = repository.getArticlesByPurchaseTypeAndTag(params, isFree, tag);
        } else {
            articles = repository.getArticlesByPurchaseTypeAndCaption(params, isFree, caption);
        }
        return convertArticles(articles);
    }

    private ArticleDto convert(Article source) {
        ArticleDto target = new ArticleDto();
        target.setId(source.getId());
        target.setCaption(source.getCaption());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setCurrency(source.getCurrency());
        target.setIsFree(source.getIsFree());

        return target;
    }

    private PaginatedResult<ArticleDto> convertArticles(PaginatedResult<Article> source) {
        return new PaginatedResult<>(source.getEntities().stream()
                .map(this::convert)
                .collect(Collectors.toList()),
                source.getRowsCount());
    }
}