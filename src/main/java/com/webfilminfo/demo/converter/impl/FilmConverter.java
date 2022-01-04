package com.webfilminfo.demo.converter.impl;

import com.webfilminfo.demo.converter.IFilmConverter;
import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.entity.FilmEntity;
import com.webfilminfo.demo.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmConverter implements IFilmConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public FilmDto entiFilmToDto(FilmEntity entity) {
        if(entity == null)
            return null;
        FilmDto dto = modelMapper.map(entity,FilmDto.class);
        return dto;
    }

    public FilmDto entiFilmToDto(FilmEntity entity, List<CommentDto> commentDtos) {
        if(entity == null)
            return null;
        FilmDto dto = modelMapper.map(entity,FilmDto.class);
        dto.setComments(commentDtos);
        return dto;
    }

    @Override
    public FilmEntity dtoFilmToEntity(FilmDto dto) {
        if(dto == null)
            return null;
        modelMapper.map(dto, FilmEntity.class);
        return modelMapper.map(dto,FilmEntity.class);
    }

    @Override
    public FilmEntity dtoFilmToEntity(FilmEntity entity, FilmDto dto) {
        if(entity == null || dto == null)
            return null;
        FilmEntity newEntity = modelMapper.map(dto, FilmEntity.class);
        newEntity.setCreateDate(entity.getCreateDate());
        newEntity.setCreateBy(entity.getCreateBy());
        return newEntity;
    }
}
