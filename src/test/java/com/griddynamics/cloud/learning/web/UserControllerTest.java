package com.griddynamics.cloud.learning.web;

import com.griddynamics.cloud.learning.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void shouldSaveUserWhenAllValuesAreProvided() throws Exception {

        final String username = "testUser";
        final String email = "test@gmail.com";
        final String password = "testPassword";

        this.mockMvc.perform(post("/sign-up")
                .param("username", username)
                .param("email", email)
                .param("password", password))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenWrongValuesAreProvided() throws Exception {

        final String username = "testUser";
        final String test = "test@gmail.com";
        final String password = "testPassword";

        this.mockMvc.perform(post("/sign-up")
                .param("username", username)
                .param("test", test)
                .param("password", password))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenNotAllValuesAreProvided() throws Exception {

        final String username = "testUser";
        final String password = "testPassword";

        this.mockMvc.perform(post("/sign-up")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isBadRequest());
    }
}
