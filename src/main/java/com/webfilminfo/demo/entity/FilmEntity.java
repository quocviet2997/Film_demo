package com.webfilminfo.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="film")
@Data
public class FilmEntity extends BaseEntity {
    @Column(name="title", nullable = false, unique = true)
    private String title;
    @Column(name="poster")
    private String poster;
    @Column(name="year")
    private Integer year;
    @Column(name="shortDescription")
    private String shortDescription;
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    @Column(name="avgVote")
    private Double avgVote;
    @OneToMany(mappedBy = "filmId")
    @JsonIgnore
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "filmId")
    @JsonIgnore
    private List<CategoryFilmEntity> filmCategories;
}
