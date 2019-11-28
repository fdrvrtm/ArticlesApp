package com.griddynamics.cloud.learning.service;

import com.griddynamics.cloud.learning.web.ArticleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService service;

    @Test
    public void shouldReturnBadRequestWhenTypeisInvalid() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/articles?type=None"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldReturnBadRequestWhenTypeIsMissed() throws Exception {
        this.mockMvc.perform(get("/articles?caption=CAPTION&tag=TAG&page=5&limit=20"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldReturnOkWhenAllFieldsAreValid() throws Exception {
        this.mockMvc.perform(get("/articles?type=ALL&caption=CAPTION&tag=TAG&page=5&limit=20&sort=date,desc"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void shouldReturnOkWhenOnlyTypeIsPresent() throws Exception {
        this.mockMvc.perform(get("/articles?type=ALL")).andExpect(status().is(HttpStatus.OK.value()));
    }
}
