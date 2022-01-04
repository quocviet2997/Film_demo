package com.webfilminfo.demo.service;

import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IFilmService {
    FilmDto findById(Long id);
    ResponseEntity<ResponseObject> findAll();
    FilmDto findByName(Long id);
    ResponseEntity<ResponseObject> findAll(Integer page, Integer limit);
    ResponseEntity<ResponseObject> findAll(Long id, Integer page, Integer limit);
    ResponseEntity<ResponseObject> save(FilmDto filmDto);
    ResponseEntity<ResponseObject> delete(Long id);
    Integer totalItem();
}
