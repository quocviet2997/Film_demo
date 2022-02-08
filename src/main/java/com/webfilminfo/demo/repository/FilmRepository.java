package com.webfilminfo.demo.repository;

import com.webfilminfo.demo.entity.CommentEntity;
import com.webfilminfo.demo.entity.FilmEntity;
import com.webfilminfo.demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
    Page<FilmEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<FilmEntity> findByTitleContainingIgnoreCase(String title);
    long countByTitleContainingIgnoreCase(String title);
    @Query(value = "select distinct f.* from film as f, category as ca, category_film as caf where caf.category_id = ?1 and caf.film_id = f.id",
            countQuery = "select count(distinct(f.id)) from film as f, category as ca, category_film as caf where caf.category_id = ?1 and caf.film_id = f.id",
            nativeQuery = true)
    Page<FilmEntity> findFilmByCategory(Long categoryId, Pageable pageable);

    @Query(value = "select distinct f.* from film as f, category as ca, category_film as caf where caf.category_id = ?1 and caf.film_id = f.id",
            nativeQuery = true)
    List<FilmEntity> findFilmByCategory(Long categoryId);
}
