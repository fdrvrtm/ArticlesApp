package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.PaginatedResult;
import com.griddynamics.cloud.learning.dao.repository.ArticleRepository;
import com.griddynamics.cloud.learning.web.dto.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArticleServiceTest {

    private final ArticleService service = new ArticleService(new ArticleRepositoryMock());

    private final Article freeWithCaptionAndTag = Article.builder()
            .id(1L)
            .caption("Article with tag and caption")
            .description("freeWithCaptionAndTag")
            .content("Content")
            .isFree(true)
            .price(0.0)
            .currency("")
            .build();

    private final Article paidWithCaption = Article.builder()
            .id(2L)
            .caption("Article with caption")
            .description("paidWithCaption")
            .isFree(false)
            .price(3.50)
            .authorId(1234L)
            .currency("euro")
            .build();

    private final Article freeWithTag = Article.builder()
            .id(3L)
            .caption("Article with tag")
            .description("freeWithTag")
            .date(LocalDateTime.now())
            .isFree(true)
            .price(0.0)
            .currency("")
            .build();

    private final Article paidWithTag = Article.builder()
            .id(4L)
            .caption("Paid with tag")
            .description("paidWithTag")
            .isFree(false)
            .price(5.0)
            .currency("usd")
            .build();

    @Test
    public void shouldGetArticlesWithRequiredParams() {
        //given
        List<Article> initialArticles = List.of(freeWithCaptionAndTag, paidWithCaption, freeWithTag, paidWithTag);
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 4);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.ALL, "", "");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(20), articles.getRowsCount());
        assertEquals(initialArticles.size(), entities.size());

        validateArticles(initialArticles, entities);
    }


    @Test
    public void shouldGetArticlesByTagWithRequiredParams() {
        //given
        List<Article> initialArticles = List.of(freeWithTag, freeWithCaptionAndTag, paidWithTag);
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 3);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.ALL, "", "tag");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(9), articles.getRowsCount());
        assertEquals(initialArticles.size(), entities.size());

        validateArticles(initialArticles, entities);
    }

    @Test
    public void shouldGetArticlesByCaptionWithRequiredParams() {
        //given
        List<Article> initialArticles = List.of(freeWithCaptionAndTag, paidWithCaption);
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 2);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.ALL, "caption", "");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(10), articles.getRowsCount());
        assertEquals(initialArticles.size(), entities.size());

        validateArticles(initialArticles, entities);
    }

    @Test
    public void shouldGetArticlesByTagAndCaptionWithRequiredParams() {
        //given
        List<Article> initialArticles = List.of(freeWithCaptionAndTag);
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 1);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.ALL, "caption", "tag");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(3), articles.getRowsCount());
        assertEquals(initialArticles.size(), entities.size());

        validateArticles(initialArticles, entities);
    }

    @Test
    public void shouldGetArticlesByPurchaseTypeWithRequiredParams() {
        //given
        List<Article> initialArticles = List.of(paidWithCaption, paidWithTag);
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 2);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.PAID, "", "");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(8), articles.getRowsCount());
        assertEquals(initialArticles.size(), entities.size());

        validateArticles(initialArticles, entities);
    }

    @Test
    public void shouldGetArticlesByPurchaseTypeAndTagWithRequiredParams() {
        //given
        List<Article> initialArticles = List.of(paidWithTag);
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 1);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.PAID, "", "tag");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(7), articles.getRowsCount());
        assertEquals(initialArticles.size(), entities.size());

        validateArticles(initialArticles, entities);
    }

    @Test
    public void shouldGetArticlesByPurchaseTypeAndCaptionWithRequiredParams() {
        //given
        List<Article> initialArticles = List.of(paidWithCaption);
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 1);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.PAID, "caption", "");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(2), articles.getRowsCount());
        assertEquals(initialArticles.size(), entities.size());

        validateArticles(initialArticles, entities);
    }

    @Test
    public void shouldGetArticlesByPurchaseTypeAndTagAndCaptionWithRequiredParams() {
        //given
        PaginatedRequestParams params = new PaginatedRequestParams(SortColumn.DATE, SortDirection.DESC, 1, 5);
        ArticleFilterRequest request = new ArticleFilterRequest(params, PurchaseType.PAID, "caption", "tag");

        //when
        final PaginatedResult<ArticleDto> articles = service.findArticles(request);

        //then
        final List<ArticleDto> entities = articles.getEntities();
        assertEquals(Integer.valueOf(0), articles.getRowsCount());
        assertTrue(entities.isEmpty());
    }

    private void validateArticles(List<Article> articles, List<ArticleDto> dtos) {
        for (Article article : articles) {
            Optional<ArticleDto> dto = dtos.stream().filter(e -> e.getId().equals(article.getId())).findFirst();
            assertTrue(dto.isPresent());
            compareFields(article, dto.get());
        }
    }

    private void compareFields(Article article, ArticleDto dto) {
        assertEquals(article.getId(), dto.getId());
        assertEquals(article.getCaption(), dto.getCaption());
        assertEquals(article.getDescription(), dto.getDescription());
        assertEquals(article.getPrice(), dto.getPrice());
        assertEquals(article.getCurrency(), dto.getCurrency());
        assertEquals(article.getIsFree(), dto.getIsFree());
    }

    private class ArticleRepositoryMock extends ArticleRepository {

        @Override
        public PaginatedResult<Article> getArticles(PaginatedRequestParams params) {
            return new PaginatedResult<>(List.of(freeWithCaptionAndTag, paidWithCaption, freeWithTag, paidWithTag), 20);
        }

        @Override
        public PaginatedResult<Article> getArticlesByPurchaseType(PaginatedRequestParams params, Boolean isFree) {
            return new PaginatedResult<>(List.of(paidWithCaption, paidWithTag), 8);
        }

        @Override
        public PaginatedResult<Article> getArticlesByTag(PaginatedRequestParams params, String tag) {
            return new PaginatedResult<>(List.of(freeWithTag, freeWithCaptionAndTag, paidWithTag), 9);
        }

        @Override
        public PaginatedResult<Article> getArticlesByPurchaseTypeAndTag(PaginatedRequestParams params, Boolean isFree, String tag) {
            return new PaginatedResult<>(List.of(paidWithTag), 7);
        }

        @Override
        public PaginatedResult<Article> getArticlesByCaption(PaginatedRequestParams params, String caption) {
            return new PaginatedResult<>(List.of(freeWithCaptionAndTag, paidWithCaption), 10);
        }

        @Override
        public PaginatedResult<Article> getArticlesByPurchaseTypeAndCaption(PaginatedRequestParams params, Boolean free, String caption) {
            return new PaginatedResult<>(List.of(paidWithCaption), 2);
        }

        @Override
        public PaginatedResult<Article> getArticlesByTagAndCaption(PaginatedRequestParams params, String tag, String caption) {
            return new PaginatedResult<>(List.of(freeWithCaptionAndTag), 3);
        }

        @Override
        public PaginatedResult<Article> getArticlesByPurchaseTypeAndTagAndCaption(PaginatedRequestParams params, Boolean isFree, String tag, String caption) {
            return new PaginatedResult<>(Collections.emptyList(), 0);
        }
    }
}