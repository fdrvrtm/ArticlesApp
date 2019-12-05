package com.griddynamics.cloud.learning.web.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    private String caption;
    private String description;
    private Double price;
    private String currency;
    private Boolean isFree;
    private Set<String> tags;
}