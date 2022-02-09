package com.webfilminfo.demo.service.impl;

import com.webfilminfo.demo.constant.Code;
import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.constant.Paging;
import com.webfilminfo.demo.converter.impl.CategoryConverter;
import com.webfilminfo.demo.dto.CategoryDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.entity.CategoryEntity;
import com.webfilminfo.demo.entity.CommentEntity;
import com.webfilminfo.demo.repository.CategoryRepository;
import com.webfilminfo.demo.repository.FilmRepository;
import com.webfilminfo.demo.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public ResponseEntity<ResponseObject> findAll() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        if(categories.size() > 0){
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for(CategoryEntity category : categories){
                categoryDtos.add(categoryConverter.entityCategoryToDto(category));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, categoryDtos)
            );
        }

        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(Integer page, Integer limit) {
        if(page == null && limit == null){
            return findAll();
        }
        if(page == null)
            page = Paging.PAGE_DEFAULT;
        if(limit == null)
            limit = Paging.LIMIT_CATEGORY;
        Pageable pageable = PageRequest.of(page-1, limit);
        Integer totalPage = (int)Math.ceil((double)countAll()/limit);
        List<CategoryEntity> categories = categoryRepository.findAll(pageable).getContent();
        if(categories.size() > 0){
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for(CategoryEntity category : categories){
                categoryDtos.add(categoryConverter.entityCategoryToDto(category));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, totalPage, limit, page, categoryDtos)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, totalPage, limit, page,null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(Integer page, Integer limit, Integer categoryId) {
        return null;
    }

    @Override
    public CategoryDto findById(Long id) {
        CategoryEntity entity = categoryRepository.findById(id).orElse(null);
        return categoryConverter.entityCategoryToDto(entity);
    }

    @Override
    public Long countAll() {
        return categoryRepository.count();
    }

    @Override
    public ResponseEntity<ResponseObject> save(CategoryDto categoryDto) {
        CategoryEntity entity = null;
        if(categoryDto.getId() == null){
            entity = categoryConverter.dtoCategoryToEntity(categoryDto);
            entity = categoryRepository.save(entity);
            return (entity == null)?
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject(Code.FAILED, Message.INSERT_FAIL, null)
                    ):
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(Code.OK, Message.INSERT_SUCCESS, entity)
                    );
        }
        else {
            entity = categoryRepository.findById(categoryDto.getId()).orElse(null);
            if (entity != null)
                entity = categoryConverter.dtoCategoryToEntity(entity, categoryDto);
            entity = categoryRepository.save(entity);
            return entity == null ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ResponseObject(Code.NOT_FOUND, Message.UPDATE_NOT_FOUND_ID, null)
                    ) :
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(Code.OK, Message.UPDATE_SUCCESS, entity)
                    );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> delete(Long id) {
        CategoryEntity temp = categoryRepository.findById(id).orElse(null);
        if(temp==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(Code.NOT_FOUND, Message.DELETE_NOT_FOUND_ID, null)
            );
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.DELETE_SUCCESS, temp)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findCategoryById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
        return (categoryEntity==null)?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1, null)):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, categoryEntity));
    }
}
