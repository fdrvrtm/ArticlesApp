package com.griddynamics.cloud.learning.web.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRolesDto extends UserDto {

    private Set<String> permissions;

    @Builder
    public UserWithRolesDto(Long id, String username, String email, Set<String> permissions) {
        super(id, username, email);
        this.permissions = permissions;
    }
}
