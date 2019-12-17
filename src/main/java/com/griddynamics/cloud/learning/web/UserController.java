package com.griddynamics.cloud.learning.web;

import com.griddynamics.cloud.learning.service.UserService;
import com.griddynamics.cloud.learning.web.dto.NewUserDto;
import com.griddynamics.cloud.learning.web.dto.UserWithRolesDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    private UserService service;

    public UserController(UserService service) { this.service = service; }

    @PostMapping(value = "/sign-up")
    public UserWithRolesDto saveUser(@Valid NewUserDto user) {
        return service.saveUser(user);
    }
}
