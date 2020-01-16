package com.griddynamics.cloud.learning.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleLikeDto {

    @NotNull
    private Long articleId;

    @NotNull
    private Boolean liked;
}
