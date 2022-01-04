package com.webfilminfo.demo.repository;

import com.webfilminfo.demo.entity.FilmEntity;
import com.webfilminfo.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<FilmEntity, Long> {
}
