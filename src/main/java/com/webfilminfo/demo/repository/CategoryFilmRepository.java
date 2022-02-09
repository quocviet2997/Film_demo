package com.webfilminfo.demo.repository;

import com.webfilminfo.demo.entity.CategoryFilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryFilmRepository extends JpaRepository<CategoryFilmEntity, Long> {
    List<CategoryFilmEntity> findByCategoryId(Long categoryId);
    List<CategoryFilmEntity> findByFilmId(Long filmId);
}
