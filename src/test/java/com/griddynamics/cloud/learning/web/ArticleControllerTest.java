package com.griddynamics.cloud.learning.web;

import com.griddynamics.cloud.learning.service.ArticleService;
import com.griddynamics.cloud.learning.web.dto.ArticleFilterRequest;
import com.griddynamics.cloud.learning.web.dto.ArticleLikeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
@WithMockUser
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService service;

    @Test
    public void shouldReturnBadRequestWhenTypeisInvalid() throws Exception {
        this.mockMvc.perform(get("/articles?type=None"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenTypeIsMissed() throws Exception {
        this.mockMvc.perform(get("/articles?caption=CAPTION&tag=TAG&page=5&size=20"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnOkWhenAllFieldsAreValid() throws Exception {
        this.mockMvc.perform(get("/articles?type=ALL&caption=CAPTION&tag=TAG&page=5&size=20&sort=date,desc"))
                .andExpect(status().isOk());

        verify(service, times(1)).findArticles(any(ArticleFilterRequest.class), any(Pageable.class));
    }

    @Test
    public void shouldReturnOkWhenOnlyTypeIsPresent() throws Exception {
        this.mockMvc.perform(get("/articles?type=ALL")).andExpect(status().isOk());

        verify(service, times(1)).findArticles(any(ArticleFilterRequest.class), any(Pageable.class));
    }

    @Test
    public void shouldReturnOkWhenLikeAnArticleWithValidValues() throws Exception{

        final String articleId = "24";
        final String liked = "true";

        this.mockMvc.perform(put("/articles/like")
                .param("articleId", articleId)
                .param("liked", liked))
                .andExpect(status().isOk());

        verify(service, times(1)).likeAnArticle(any(ArticleLikeDto.class));
    }

    @Test
    public void shouldReturnBadRequestWhenLikeAnArticleWithMissingValues() throws Exception{

        final String articleId = "24";

        this.mockMvc.perform(put("/articles/like")
                .param("articleId", articleId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenLikeAnArticleWithWrongValues() throws Exception {

        final String articleId = "24";
        final String wrongValue = "Wrong Value";

        this.mockMvc.perform(put("/articles/like")
                .param("articleId", articleId)
                .param("wrongValue", wrongValue))
                .andExpect(status().isBadRequest());
    }
}
