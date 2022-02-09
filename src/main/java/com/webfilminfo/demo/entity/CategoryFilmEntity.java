package com.webfilminfo.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="categoryFilm")
@Data
public class CategoryFilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name="filmId", insertable = false, updatable = false, nullable = false)
    @ManyToOne(targetEntity = FilmEntity.class, fetch = FetchType.LAZY )
    private FilmEntity film;
    @JoinColumn(name="categoryId", insertable = false, updatable = false, nullable = false)
    @ManyToOne(targetEntity = CategoryEntity.class, fetch = FetchType.LAZY )
    private CategoryEntity category;
    @Column(name="filmId", nullable = false)
    private Long filmId;
    @Column(name="categoryId", nullable = false)
    private Long categoryId;

    public void setFilm(FilmEntity film) {
        setCategoryId(film.getId());
        this.film = film;
    }
    public void setCategory(CategoryEntity category) {
        setCategoryId(category.getId());
        this.category = category;
    }
}
