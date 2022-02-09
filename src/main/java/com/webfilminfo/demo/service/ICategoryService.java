package com.webfilminfo.demo.service;

import com.webfilminfo.demo.dto.CategoryDto;
import com.webfilminfo.demo.dto.ResponseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    ResponseEntity<ResponseObject> findAll();
    ResponseEntity<ResponseObject> findAll(Integer page, Integer limit);
    ResponseEntity<ResponseObject> findAll(Integer page, Integer limit, Integer categoryId);
    CategoryDto findById(Long id);
    Long countAll();
    ResponseEntity<ResponseObject> save(CategoryDto categoryDto);
    ResponseEntity<ResponseObject> delete(Long id);
    ResponseEntity<ResponseObject> findCategoryById(Long id);
}
