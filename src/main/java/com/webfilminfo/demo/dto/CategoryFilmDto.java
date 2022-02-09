package com.webfilminfo.demo.dto;

import lombok.Data;

@Data
public class CategoryFilmDto {
    private Long id;
    private Long categoryId;
    private Long filmId;
    private String categoryName;
    private String filmName;
}
