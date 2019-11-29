package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.Tag;
import com.griddynamics.cloud.learning.dao.repository.ArticleRepository;
import com.griddynamics.cloud.learning.web.dto.ArticleDto;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;
import com.griddynamics.cloud.learning.web.dto.PurchaseType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository repository;

    @Mock
    private ArticleMapperImpl mapper;

    @InjectMocks
    private ArticleService service;

    @Test
    public void shouldGetAllArticles() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.ALL, "", "");
        final Pageable pageable = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("date")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content").build();
        final Article expectedArticle2 = Article.builder().id(2L).caption("second").content("second content").build();
        final Article expectedArticle3 = Article.builder().id(3L).caption("third").content("third content").build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first").build();
        final ArticleDto expectedArticleDto2 = ArticleDto.builder().id(2L).caption("second").build();
        final ArticleDto expectedArticleDto3 = ArticleDto.builder().id(3L).caption("third").build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1, expectedArticle2, expectedArticle3),
                pageable, 50);

        when(repository.findAll(pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);
        when(mapper.articleToArticleDto(expectedArticle2)).thenReturn(expectedArticleDto2);
        when(mapper.articleToArticleDto(expectedArticle3)).thenReturn(expectedArticleDto3);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAll(pageable);

        assertEquals(50, articles.getTotalElements());
        assertEquals(17, articles.getTotalPages());
        assertEquals(3, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1, expectedArticleDto2, expectedArticleDto3));
    }

    @Test
    public void shouldGetArticlesByPurchaseType() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.FREE, "", "");
        final Pageable pageable = PageRequest.of(4, 2, Sort.by(Sort.Order.desc("caption")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content").isFree(true).build();
        final Article expectedArticle2 = Article.builder().id(2L).caption("second").content("second content").isFree(true).build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first").isFree(true).build();
        final ArticleDto expectedArticleDto2 = ArticleDto.builder().id(2L).caption("second").isFree(true).build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1, expectedArticle2),
                pageable, 10);

        when(repository.findAllByIsFree(true, pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);
        when(mapper.articleToArticleDto(expectedArticle2)).thenReturn(expectedArticleDto2);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAllByIsFree(true, pageable);

        assertEquals(10, articles.getTotalElements());
        assertEquals(5, articles.getTotalPages());
        assertEquals(2, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1, expectedArticleDto2));
    }

    @Test
    public void shouldGetAllArticlesByCaption() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.ALL, "s", "");
        final Pageable pageable = PageRequest.of(1, 2, Sort.by(Sort.Order.desc("description")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content").build();
        final Article expectedArticle2 = Article.builder().id(2L).caption("second").content("second content").build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first").build();
        final ArticleDto expectedArticleDto2 = ArticleDto.builder().id(2L).caption("second").build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1, expectedArticle2),
                pageable, 8);

        when(repository.findAllByCaptionContaining("s", pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);
        when(mapper.articleToArticleDto(expectedArticle2)).thenReturn(expectedArticleDto2);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAllByCaptionContaining("s", pageable);

        assertEquals(8, articles.getTotalElements());
        assertEquals(4, articles.getTotalPages());
        assertEquals(2, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1, expectedArticleDto2));
    }

    @Test
    public void shouldGetArticlesByCaptionAndPurchaseType() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.PAID, "s", "");
        final Pageable pageable = PageRequest.of(9, 2, Sort.by(Sort.Order.desc("currency")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content")
                .isFree(false).price(2.5).currency("usd").build();
        final Article expectedArticle2 = Article.builder().id(2L).caption("second").content("second content")
                .isFree(false).price(2.25).currency("euro").build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first")
                .isFree(false).price(2.5).currency("usd").build();
        final ArticleDto expectedArticleDto2 = ArticleDto.builder().id(2L).caption("second")
                .isFree(false).price(2.25).currency("euro").build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1, expectedArticle2),
                pageable, 24);

        when(repository.findAllByIsFreeAndCaptionContaining(false, "s", pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);
        when(mapper.articleToArticleDto(expectedArticle2)).thenReturn(expectedArticleDto2);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAllByIsFreeAndCaptionContaining(false, "s", pageable);

        assertEquals(24, articles.getTotalElements());
        assertEquals(12, articles.getTotalPages());
        assertEquals(2, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1, expectedArticleDto2));
    }

    @Test
    public void shouldGetAllArticlesByTag() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.ALL, "", "math");
        final Pageable pageable = PageRequest.of(3, 2, Sort.by(Sort.Order.desc("description")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content")
                .tags(Set.of(Tag.builder().id(1L).name("math").build())).build();
        final Article expectedArticle2 = Article.builder().id(2L).caption("second").content("second content")
                .tags(Set.of(Tag.builder().id(1L).name("math").build())).build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first")
                .tags(Set.of("math")).build();
        final ArticleDto expectedArticleDto2 = ArticleDto.builder().id(2L).caption("second")
                .tags(Set.of("math")).build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1, expectedArticle2),
                pageable, 14);

        when(repository.findAllByTag("math", pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);
        when(mapper.articleToArticleDto(expectedArticle2)).thenReturn(expectedArticleDto2);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAllByTag("math", pageable);

        assertEquals(14, articles.getTotalElements());
        assertEquals(7, articles.getTotalPages());
        assertEquals(2, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1, expectedArticleDto2));
    }

    @Test
    public void shouldGetArticlesByTagAndPurchaseType() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.FREE, "", "math");
        final Pageable pageable = PageRequest.of(4, 2, Sort.by(Sort.Order.desc("free")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content").isFree(true)
                .tags(Set.of(Tag.builder().id(1L).name("math").build())).build();
        final Article expectedArticle2 = Article.builder().id(2L).caption("second").content("second content").isFree(true)
                .tags(Set.of(Tag.builder().id(1L).name("math").build())).build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first").isFree(true)
                .tags(Set.of("math")).build();
        final ArticleDto expectedArticleDto2 = ArticleDto.builder().id(2L).caption("second").isFree(true)
                .tags(Set.of("math")).build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1, expectedArticle2),
                pageable, 18);

        when(repository.findAllByIsFreeAndTag(true, "math", pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);
        when(mapper.articleToArticleDto(expectedArticle2)).thenReturn(expectedArticleDto2);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAllByIsFreeAndTag(true, "math", pageable);

        assertEquals(18, articles.getTotalElements());
        assertEquals(9, articles.getTotalPages());
        assertEquals(2, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1, expectedArticleDto2));
    }

    @Test
    public void shouldGetAllArticlesByTagAndCaption() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.ALL, "s", "math");
        final Pageable pageable = PageRequest.of(4, 2, Sort.by(Sort.Order.desc("description")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content")
                .tags(Set.of(Tag.builder().id(1L).name("math").build())).build();
        final Article expectedArticle2 = Article.builder().id(2L).caption("second").content("second content")
                .tags(Set.of(Tag.builder().id(1L).name("math").build())).build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first")
                .tags(Set.of("math")).build();
        final ArticleDto expectedArticleDto2 = ArticleDto.builder().id(2L).caption("second")
                .tags(Set.of("math")).build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1, expectedArticle2),
                pageable, 10);

        when(repository.findAllByTagAndCaption("math", "s", pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);
        when(mapper.articleToArticleDto(expectedArticle2)).thenReturn(expectedArticleDto2);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAllByTagAndCaption("math", "s", pageable);

        assertEquals(10, articles.getTotalElements());
        assertEquals(5, articles.getTotalPages());
        assertEquals(2, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1, expectedArticleDto2));
    }

    @Test
    public void shouldGetAllArticlesByPurchaseTypeAndTagAndCaption() {
        //given
        final ArticleFilterRequest request = new ArticleFilterRequest(PurchaseType.PAID, "s", "math");
        final Pageable pageable = PageRequest.of(7, 1, Sort.by(Sort.Order.desc("description")));

        final Article expectedArticle1 = Article.builder().id(1L).caption("first").content("first content")
                .isFree(false).price(2.5).currency("usd")
                .tags(Set.of(Tag.builder().id(1L).name("math").build())).build();

        final ArticleDto expectedArticleDto1 = ArticleDto.builder().id(1L).caption("first")
                .isFree(false).price(2.5).currency("usd")
                .tags(Set.of("math")).build();

        final PageImpl<Article> articlesPage = new PageImpl<>(List.of(expectedArticle1),
                pageable, 8);

        when(repository.findAllByIsFreeAndTagAndCaption(false, "math", "s", pageable)).thenReturn(articlesPage);
        when(mapper.articleToArticleDto(expectedArticle1)).thenReturn(expectedArticleDto1);

        //when
        final Page<ArticleDto> articles = service.findArticles(request, pageable);

        //then
        verify(repository, times(1)).findAllByIsFreeAndTagAndCaption(false, "math", "s", pageable);

        assertEquals(8, articles.getTotalElements());
        assertEquals(8, articles.getTotalPages());
        assertEquals(1, articles.getContent().size());
        assertEquals(new HashSet<>(articles.getContent()), Set.of(expectedArticleDto1));
    }
}
