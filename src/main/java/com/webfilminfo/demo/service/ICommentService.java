package com.webfilminfo.demo.service;

import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICommentService {
    CommentDto findById(Long id);
    List<CommentDto> findByFilmId(Long filmId);
    List<CommentDto> findByFilmId(Integer page, Integer limit, Long filmId);
    ResponseEntity<ResponseObject> findCommentById(Long id);
    ResponseEntity<ResponseObject> findAllWithReplyIdIsNull();
    ResponseEntity<ResponseObject> findAll();
    ResponseEntity<ResponseObject> findAllWithReplyIdIsNull(Integer page, Integer limit);
    ResponseEntity<ResponseObject> findAll(Integer page, Integer limit);
    ResponseEntity<ResponseObject> save(CommentDto filmDto);
    ResponseEntity<ResponseObject> delete(Long id);
    Integer totalItem();
    Integer totalItem(Long id);
}
