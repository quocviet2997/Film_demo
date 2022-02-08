package com.webfilminfo.demo.service.impl;

import com.webfilminfo.demo.constant.Code;
import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.constant.Paging;
import com.webfilminfo.demo.converter.impl.CommentConverter;
import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.entity.CommentEntity;
import com.webfilminfo.demo.repository.CommentRepository;
import com.webfilminfo.demo.repository.FilmRepository;
import com.webfilminfo.demo.repository.UserRepository;
import com.webfilminfo.demo.service.ICommentService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentConverter commentConverter;

    @Override
    public CommentDto findById(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElse(null);
        return commentConverter.entiCommentToDto(commentEntity);
    }

    @Override
    public ResponseEntity<ResponseObject> findCommentById(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id).orElse(null);
        return (commentEntity==null)?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1, null)):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, commentEntity));
    }
    @Override
    public List<CommentDto> findByFilmId(Long filmId) {
        List<CommentEntity> commentEntities = commentRepository.findByFilmIdAndReplyIdIsNull(filmId);
        if(commentEntities.size() == 0)
            return null;
        List<CommentDto> commentDtos = commentConverter.entiListCommentToDto(commentEntities);
        return commentDtos;
    }

    @Override
    public List<CommentDto> findByFilmId(Integer page, Integer limit, Long filmId) {
        if(page == null && limit == null)
            return findByFilmId(filmId);
        if(limit == null){
            limit = Paging.LIMIT_COMMENT;
        }
        if(page == null){
            page = Paging.PAGE_DEFAULT;
        }
        Pageable pageable = PageRequest.of(page-1, limit);
        List<CommentEntity> commentEntities = commentRepository.findByFilmIdAndReplyIdIsNull(filmId, pageable).getContent();
        if(commentEntities.size() == 0)
            return null;
        List<CommentDto> commentDtos = commentConverter.entiListCommentToDto(commentEntities);
        return commentDtos;
    }

    @Override
    public ResponseEntity<ResponseObject> findAllWithReplyIdIsNull() {
        List<CommentEntity> commentEntities = commentRepository.findByReplyIdIsNull();
        if(commentEntities.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1, null)
            );
        }
        List<CommentDto> commentDtos = commentConverter.entiListCommentToDto(commentEntities);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, commentDtos)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAll() {
        List<CommentEntity> commentEntities = commentRepository.findAll();
        if(commentEntities.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1, null)
            );
        }
        List<CommentDto> commentDtos = commentConverter.entiListCommentToDto(commentEntities);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, commentDtos)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAllWithReplyIdIsNull(Integer page, Integer limit) {
        if(page == null && limit == null)
            return findAllWithReplyIdIsNull();
        if(limit == null){
            limit = Paging.LIMIT_COMMENT;
        }
        if(page == null){
            page = Paging.PAGE_DEFAULT;
        }
        Pageable pageable = PageRequest.of(page-1, limit);
        int totalItem = totalItem();
        Integer totalPage = (int)Math.ceil((double)totalItem()/limit);
        List<CommentEntity> commentEntities = commentRepository.findByReplyIdIsNull(pageable).getContent();
        if(commentEntities.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, totalPage, limit, page,null)
            );
        }
        List<CommentDto> commentDtos = commentConverter.entiListCommentToDto(commentEntities);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.FIND_SUCCESS, totalPage, limit, page, commentDtos)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(Integer page, Integer limit) {
        if(page == null && limit == null)
            return findAll();
        if(limit == null){
            limit = Paging.LIMIT_COMMENT;
        }
        if(page == null){
            page = Paging.PAGE_DEFAULT;
        }
        Pageable pageable = PageRequest.of(page-1, limit);
        long totalItem = commentRepository.count();
        Integer totalPage = (int)Math.ceil((double)totalItem/limit);
        List<CommentEntity> commentEntities = commentRepository.findAll(pageable).getContent();
        if(commentEntities.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, totalPage, limit, page,null)
            );
        }
        List<CommentDto> commentDtos = commentConverter.entiListCommentToDto(commentEntities);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.FIND_SUCCESS, totalPage, limit, page, commentDtos)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> save(CommentDto commentDto) {
        Long id = commentDto.getId();
        if(filmRepository.findById(commentDto.getFilmId()).orElse(null) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(Code.FAILED, Message.INSERT_FAIL_FILMID, null)
            );
        }
        if(userRepository.findById(commentDto.getUserId()).orElse(null) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(Code.FAILED, Message.INSERT_FAIL_USERID, null)
            );
        }
        CommentEntity entity = null;
        if(id == null){
            entity = commentConverter.dtoCommentToEntity(commentDto);
            entity = commentRepository.save(entity);
            return (entity == null)?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject(Code.FAILED, Message.INSERT_FAIL, null)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Code.OK, Message.INSERT_SUCCESS, entity)
                );
        }
        else {
            entity = commentRepository.findById(id).orElse(null);
            if (commentRepository.findById(id) != null)
                entity = commentConverter.dtoCommentToEntity(entity, commentDto);
                entity = commentRepository.save(entity);
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
        CommentEntity temp = commentRepository.findById(id).orElse(null);
        if(temp==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(Code.NOT_FOUND, Message.DELETE_NOT_FOUND_ID, null)
            );
        }
        commentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.DELETE_SUCCESS, temp)
        );
    }

    @Override
    public Integer totalItem() {
        return (int) commentRepository.countByReplyIdIsNull();
    }

    @Override
    public Integer totalItem(Long id) {
        return (int) commentRepository.countByReplyIdIsNullAndFilmId(id).longValue();
    }
}
