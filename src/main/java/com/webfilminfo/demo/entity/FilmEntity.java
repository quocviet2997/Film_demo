package com.webfilminfo.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="film")
@Data
public class FilmEntity extends BaseEntity {
    @Column(name="title")
    private String title;
    @Column(name="poster")
    private String poster;
    @Column(name = "genre")
    private String genre;
    @Column(name="year")
    private Integer year;
    @Column(name="shortDescription")
    private String shortDescription;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name="avgVote")
    private Double avgVote;
    @OneToMany(mappedBy = "filmId")
    @JsonIgnore
    private List<CommentEntity> comments;
}
