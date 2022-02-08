package com.webfilminfo.demo.service.impl;

import com.webfilminfo.demo.constant.Code;
import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.constant.Paging;
import com.webfilminfo.demo.converter.impl.CommentConverter;
import com.webfilminfo.demo.converter.impl.FilmConverter;
import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.entity.FilmEntity;
import com.webfilminfo.demo.repository.CommentRepository;
import com.webfilminfo.demo.repository.FilmRepository;
import com.webfilminfo.demo.service.ICommentService;
import com.webfilminfo.demo.service.IFilmService;
import com.webfilminfo.demo.service.IImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class FilmService implements IFilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmConverter filmConverter;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IImageStorageService imageStorageService;

    @Override
    public FilmDto findById(Long id) {
        FilmEntity entity = filmRepository.findById(id).orElse(null);
        FilmDto dto = filmConverter.entiFilmToDto(entity);
        return dto;
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(String searchInfo) {
        List<FilmEntity> entities = new ArrayList<>();
        if("".equals(searchInfo) || searchInfo == null) {
            entities = filmRepository.findAll();
        }
        else
        {
            entities = filmRepository.findByTitleContainingIgnoreCase(searchInfo);
        }
        List<FilmDto> dtos = new ArrayList<>();
        for(FilmEntity entity:entities){
            dtos.add(filmConverter.entiFilmToDto(entity));
        }
        if(dtos.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, dtos)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1,"")
        );
    }

    @Override
    public FilmDto findByName(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(Integer page, Integer limit, String searchInfo) {
        if(page == null && limit == null)
            return findAll(searchInfo);
        if(limit == null){
            limit = Paging.LIMIT_FILM;
        }
        if(page == null){
            page = Paging.PAGE_DEFAULT;
        }
        Pageable pageable = PageRequest.of(page-1, limit);
        Integer totalPage = 1;
        List<FilmEntity> entities = null;
        if("".equals(searchInfo) || searchInfo == null) {
            entities = filmRepository.findAll(pageable).getContent();
            totalPage = (int)Math.ceil((double)totalItem()/limit);
        }
        else{
            entities = filmRepository.findByTitleContainingIgnoreCase(searchInfo, pageable).getContent();
            totalPage = (int)Math.ceil((double)filmRepository.countByTitleContainingIgnoreCase(searchInfo)/limit);
        }
        List<FilmDto> dtos = new ArrayList<>();
        for(FilmEntity entity:entities){
            long id = entity.getId();
            List<CommentDto> commentDtos = commentService.findByFilmId(entity.getId());
            dtos.add(filmConverter.entiFilmToDto(entity, commentDtos));
        }

        if(dtos.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, totalPage, limit, page, dtos)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, totalPage, limit, page, null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(Long id,Integer page, Integer limit) {
        if(limit == null){
            limit = Paging.LIMIT_FILM;
        }
        if(page == null){
            page = Paging.PAGE_DEFAULT;
        }
        Integer totalPage = (int)Math.ceil((double)commentService.totalItem(id)/limit);
        FilmEntity entity = filmRepository.findById(id).orElse(null);
        if(entity != null){
            List<CommentDto> commentDtos = commentService.findByFilmId(page, limit, id);
            FilmDto dto = filmConverter.entiFilmToDto(entity, commentDtos);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, totalPage, limit, page, dto)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, totalPage, limit, page, null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAllByGenre(Long id,Integer page, Integer limit) {
        if(page == null && limit == null)
            return findAllByGenre(id);
        if(limit == null){
            limit = Paging.LIMIT_FILM;
        }
        if(page == null){
            page = Paging.PAGE_DEFAULT;
        }
        Pageable pageable = PageRequest.of(page-1, limit);
        Integer totalPage = (int)Math.ceil((double)filmRepository.findFilmByCategory(id).size()/limit);
        List<FilmEntity> entities = filmRepository.findFilmByCategory(id, pageable).getContent();
        List<FilmDto> dtos = new ArrayList<>();
        for(FilmEntity entity:entities){
            List<CommentDto> commentDtos = commentService.findByFilmId(entity.getId());
            dtos.add(filmConverter.entiFilmToDto(entity, commentDtos));
        }
        if(dtos.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, totalPage, limit, page, dtos)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, totalPage, limit, page, null)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAllByGenre(Long id) {
        List<FilmEntity> entities = filmRepository.findFilmByCategory(id);
        List<FilmDto> dtos = new ArrayList<>();
        for(FilmEntity entity:entities){
            dtos.add(filmConverter.entiFilmToDto(entity));
        }
        if(dtos.size()>0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, dtos)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1,"")
        );
    }

    @Override
    public ResponseEntity<ResponseObject> save(FilmDto filmDto, MultipartFile file) {
        FilmEntity entity = null;
        Long tempId = filmDto.getId();
        String tempStr = null;
        if(file != null)
            tempStr = imageStorageService.storeFile(file);
        if(tempId != null){
            entity = filmRepository.findById(tempId).orElse(null);
            if(entity != null){
                entity = filmConverter.dtoFilmToEntity(entity, filmDto);
                if(tempStr != null)
                    entity.setPoster(tempStr);
                entity = filmRepository.save(entity);
            }
            return (entity!= null) ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(Code.OK, Message.UPDATE_SUCCESS, entity)
                    ):
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject(Code.FAILED, Message.UPDATE_NOT_FOUND_ID, null)
                    );
        }
        else{
            entity = filmConverter.dtoFilmToEntity(filmDto);
            entity.setStatus(0);
            entity.setPoster(tempStr);
            FilmEntity temp = filmRepository.save(entity);
            return (entity!= null) ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(Code.OK, Message.INSERT_SUCCESS, entity)
                    ):
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject(Code.FAILED, Message.INSERT_FAIL, null)
                    );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> delete(Long id) {
        FilmEntity tempFilm = filmRepository.findById(id).orElse(null);
        if(tempFilm != null){
            filmRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.DELETE_SUCCESS, tempFilm)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(Code.NOT_FOUND, Message.DELETE_NOT_FOUND_ID, null)
        );
    }

    @Override
    public Integer totalItem() {
        return (int)filmRepository.count();
    }
}
