package com.webfilminfo.demo.service.impl;

import com.webfilminfo.demo.constant.Code;
import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.constant.Paging;
import com.webfilminfo.demo.converter.impl.CategoryFilmConverter;
import com.webfilminfo.demo.dto.CategoryFilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.entity.CategoryFilmEntity;
import com.webfilminfo.demo.entity.CommentEntity;
import com.webfilminfo.demo.repository.CategoryFilmRepository;
import com.webfilminfo.demo.service.ICategoryFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryFilmService implements ICategoryFilmService {
    @Autowired
    private CategoryFilmRepository categoryFilmRepository;

    @Autowired
    private CategoryFilmConverter categoryFilmConverter;

    @Override
    public ResponseEntity<ResponseObject> findByCategoryId(Long categoryId) {
        List<CategoryFilmEntity> categoryFilmEntities = categoryFilmRepository.findByCategoryId(categoryId);
        if(categoryFilmEntities.size()>0){
            List<CategoryFilmDto> categoryFilmDtos = new ArrayList<>();
            for(CategoryFilmEntity categoryFilmEntity:categoryFilmEntities){
                categoryFilmDtos.add(categoryFilmConverter.entityToDto(categoryFilmEntity));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseObject(Code.OK, Message.FIND_SUCCESS, categoryFilmDtos)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.FAILED, Message.FIND_NOT_FOUND, null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findByFilmId(Long filmId) {
        List<CategoryFilmEntity> categoryFilmEntities = categoryFilmRepository.findByFilmId(filmId);
        if(categoryFilmEntities.size()>0){
            List<CategoryFilmDto> categoryFilmDtos = new ArrayList<>();
            for(CategoryFilmEntity categoryFilmEntity:categoryFilmEntities){
                categoryFilmDtos.add(categoryFilmConverter.entityToDto(categoryFilmEntity));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, categoryFilmDtos)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.FAILED, Message.FIND_NOT_FOUND, null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAll() {
        List<CategoryFilmEntity> categories = categoryFilmRepository.findAll();
        if(categories.size() > 0){
            List<CategoryFilmDto> categoryDtos = new ArrayList<>();
            for(CategoryFilmEntity categoryFilmDto : categories){
                categoryDtos.add(categoryFilmConverter.entityToDto(categoryFilmDto));
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, categoryDtos)
            );
        }

        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(Integer page, Integer limit) {
        if(page == null)
            page = Paging.PAGE_DEFAULT;
        if(limit == null)
            limit = Paging.LIMIT_CATEGORY_FILM;

        Pageable pageable = PageRequest.of(page-1, limit);
        Integer totalPage = (int)Math.ceil((double)countAll()/limit);
        List<CategoryFilmEntity> categories = categoryFilmRepository.findAll(pageable).getContent();
        if(categories.size() > 0){
            List<CategoryFilmDto> categoryDtos = new ArrayList<>();
            for(CategoryFilmEntity categoryFilmDto : categories){
                categoryDtos.add(categoryFilmConverter.entityToDto(categoryFilmDto));
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
    public CategoryFilmDto findById(Long id) {
        CategoryFilmEntity entity = categoryFilmRepository.findById(id).orElse(null);
        return categoryFilmConverter.entityToDto(entity);
    }

    @Override
    public Long countAll() {
        return categoryFilmRepository.count();
    }

    @Override
    public ResponseEntity<ResponseObject> save(CategoryFilmDto dto) {
        CategoryFilmEntity entity = null;
        if(dto.getId() == null){
            entity = categoryFilmConverter.dtoToEntity(dto);
            entity = categoryFilmRepository.save(entity);
            return (entity == null)?
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject(Code.FAILED, Message.INSERT_FAIL, null)
                    ):
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(Code.OK, Message.INSERT_SUCCESS, entity)
                    );
        }
        else {
            entity = categoryFilmRepository.findById(dto.getId()).orElse(null);
            if (entity != null)
                entity = categoryFilmConverter.dtoToEntity(entity, dto);
            entity = categoryFilmRepository.save(entity);
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
        CategoryFilmEntity temp = categoryFilmRepository.findById(id).orElse(null);
        if(temp==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(Code.NOT_FOUND, Message.DELETE_NOT_FOUND_ID, null)
            );
        }
        categoryFilmRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.DELETE_SUCCESS, categoryFilmConverter.entityToDto(temp))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findCategoryFilmById(Long id) {
        CategoryFilmEntity categoryFilmEntity = categoryFilmRepository.findById(id).orElse(null);
        return (categoryFilmEntity==null)?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1, null)):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, categoryFilmConverter.entityToDto(categoryFilmEntity)));
    }
}
