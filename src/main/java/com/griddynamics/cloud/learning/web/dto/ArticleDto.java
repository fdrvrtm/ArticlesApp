package com.griddynamics.cloud.learning.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}