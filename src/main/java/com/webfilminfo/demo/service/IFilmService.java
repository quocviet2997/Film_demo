package com.webfilminfo.demo.service;

import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IFilmService {
    FilmDto findById(Long id);
    ResponseEntity<ResponseObject> findAll(String searchInfo);
    FilmDto findByName(Long id);
    ResponseEntity<ResponseObject> findAll(Integer page, Integer limit, String searchInfo);
    ResponseEntity<ResponseObject> findAll(Long id, Integer page, Integer limit);
    ResponseEntity<ResponseObject> findAllByGenre(Long id, Integer page, Integer limit);
    ResponseEntity<ResponseObject> findAllByGenre(Long id);
    ResponseEntity<ResponseObject> save(FilmDto filmDto, MultipartFile file);
    ResponseEntity<ResponseObject> delete(Long id);
    Integer totalItem();
}
