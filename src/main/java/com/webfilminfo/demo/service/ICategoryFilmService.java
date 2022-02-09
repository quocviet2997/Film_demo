package com.webfilminfo.demo.service;

import com.webfilminfo.demo.dto.CategoryFilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryFilmService {
    ResponseEntity<ResponseObject> findByCategoryId(Long categoryId);
    ResponseEntity<ResponseObject> findByFilmId(Long filmId);
    ResponseEntity<ResponseObject> findAll();
    ResponseEntity<ResponseObject> findAll(Integer page, Integer limit);
    CategoryFilmDto findById(Long id);
    Long countAll();
    ResponseEntity<ResponseObject> save(CategoryFilmDto categoryDto);
    ResponseEntity<ResponseObject> delete(Long id);
    ResponseEntity<ResponseObject> findCategoryFilmById(Long id);
}
