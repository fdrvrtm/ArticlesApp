package com.griddynamics.cloud.learning.web;

import com.griddynamics.cloud.learning.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldReturnBadRequestWhenTypeIsMissed() throws Exception {
        this.mockMvc.perform(get("/articles?caption=CAPTION&tag=TAG&page=5&size=20"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldReturnOkWhenAllFieldsAreValid() throws Exception {
        this.mockMvc.perform(get("/articles?type=ALL&caption=CAPTION&tag=TAG&page=5&size=20&sort=date,desc"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldReturnOkWhenOnlyTypeIsPresent() throws Exception {
        this.mockMvc.perform(get("/articles?type=ALL")).andExpect(status().is(HttpStatus.OK.value()));
    }
}
