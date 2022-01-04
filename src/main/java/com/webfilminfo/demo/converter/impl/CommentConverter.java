package com.webfilminfo.demo.converter.impl;

import com.webfilminfo.demo.converter.ICommentConverter;
import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.entity.CommentEntity;
import com.webfilminfo.demo.repository.CommentRepository;
import com.webfilminfo.demo.repository.FilmRepository;
import com.webfilminfo.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentConverter implements ICommentConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentDto entiCommentToDto(CommentEntity entity) {
        CommentDto commentDto = modelMapper.map(entity, CommentDto.class);
        Long id = entity.getId();
        if(id != null){
            List<CommentEntity> commentChildren = commentRepository.findByReplyId(id);
            commentDto.setChildComments(entiListCommentToDto(commentChildren));
            commentDto.setUserName(userRepository.findById(commentDto.getUserId()).orElse(null).getUserName());
            commentDto.setFilmName(filmRepository.findById(commentDto.getFilmId()).orElse(null).getTitle());
        }
        return commentDto;
    }

    @Override
    public List<CommentDto> entiListCommentToDto(List<CommentEntity> entities) {
        List<CommentDto> commentChildren = new ArrayList<>();
        if(entities.size() == 0)
            return null;
        for (CommentEntity entity : entities){
            commentChildren.add(entiCommentToDto(entity));
        }
        return commentChildren;
    }

    @Override
    public CommentEntity dtoCommentToEntity(CommentDto dto) {
        return modelMapper.map(dto, CommentEntity.class);
    }

    @Override
    public CommentEntity dtoCommentToEntity(CommentEntity entity, CommentDto dto) {
        entity.setComment(dto.getComment());
        return entity;
    }
}
