package com.webfilminfo.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
public class FilmDto extends BaseDto {
    private Long id;
    private String title;
    private String poster;
    private String genre;
    private Integer year;
    private String shortDescription;
    private String description;
    private Double avgVote;
    private List<CommentDto> comments;
}
