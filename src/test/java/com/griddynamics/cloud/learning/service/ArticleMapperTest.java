package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.Tag;
import com.griddynamics.cloud.learning.web.dto.ArticleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ArticleMapperTest {

    @InjectMocks
    private ArticleMapperImpl mapper;

    @Test
    public void shouldCorrectlyMapArticleToArticleDto() {

        //given
        final Long id = 1L;
        final String caption = "caption";
        final String description = "description";
        final String content = "content";
        final Double price = 3.5;
        final Long authorId = 35L;
        final String usd = "usd";
        final LocalDateTime now = LocalDateTime.now();
        final Boolean isFree = false;

        Tag firstTag = Tag.builder().id(23L).name("java").build();
        Tag secondTag = Tag.builder().id(17L).name("spring").build();

        Article initialEntity = Article.builder().id(id).caption(caption).description(description)
                .content(content).price(price).authorId(authorId).currency(usd).date(now)
                .isFree(isFree).tags(Set.of(firstTag, secondTag)).build();

        //when
        ArticleDto actualDto = mapper.articleToArticleDto(initialEntity);

        //then
        assertEquals(id, actualDto.getId());
        assertEquals(caption, actualDto.getCaption());
        assertEquals(description, actualDto.getDescription());
        assertEquals(price, actualDto.getPrice(), 0.0);
        assertEquals(usd, actualDto.getCurrency());
        assertEquals(isFree, actualDto.getIsFree());
        assertEquals(Set.of(firstTag.getName(), secondTag.getName()), actualDto.getTags());
    }
}
