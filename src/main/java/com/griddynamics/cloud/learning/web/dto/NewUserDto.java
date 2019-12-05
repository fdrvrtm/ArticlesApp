package com.griddynamics.cloud.learning.web.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto extends UserDto {

    @NotBlank
    private String password;

    @Builder
    public  NewUserDto(Long id, String username, String email, String password) {
        super(id, username, email);
        this.password = password;
    }
}
