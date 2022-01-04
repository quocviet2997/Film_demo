package com.webfilminfo.demo.service;

import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    UserDto findById(Long id);
    ResponseEntity<ResponseObject> findAll();
    ResponseEntity<ResponseObject> findUserById(Long id);
    ResponseEntity<ResponseObject> findAll(Integer page, Integer limit);
    ResponseEntity<ResponseObject> save(UserDto userDto);
    ResponseEntity<ResponseObject> delete(Long id);
    ResponseEntity<ResponseObject> login(UserDto userDto);
    Integer totalItem();
}
