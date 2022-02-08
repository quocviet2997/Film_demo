package com.webfilminfo.demo.converter.impl;

import com.webfilminfo.demo.converter.IFilmConverter;
import com.webfilminfo.demo.dto.CategoryDto;
import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.entity.CategoryEntity;
import com.webfilminfo.demo.entity.CategoryFilmEntity;
import com.webfilminfo.demo.entity.FilmEntity;
import com.webfilminfo.demo.repository.CategoryFilmRepository;
import com.webfilminfo.demo.repository.CategoryRepository;
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

    @Autowired
    private CategoryFilmRepository categoryFilmRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryConverter categoryConverter = new CategoryConverter();

    @Override
    public FilmDto entiFilmToDto(FilmEntity entity) {
        if(entity == null)
            return null;
        FilmDto dto = modelMapper.map(entity,FilmDto.class);
        List<CategoryFilmEntity> tempCategoryFilmEntity = categoryFilmRepository.findByFilmId(dto.getId());
        if(tempCategoryFilmEntity.size()>0) {
            List<CategoryDto> genres = new ArrayList<>();
            for(CategoryFilmEntity categoryFilmEntity: tempCategoryFilmEntity){
                Long filmId = categoryFilmEntity.getCategoryId();
                CategoryEntity category = categoryRepository.findById(filmId).orElse(null);
                CategoryDto genre = categoryConverter.entityCategoryToDto(category);
                if (genre != null)
                    genres.add(genre);
            }
            dto.setGenre(genres);
        }
        else{
            dto.setGenre(null);
        }
        return dto;
    }

    public FilmDto entiFilmToDto(FilmEntity entity, List<CommentDto> commentDtos) {
        if(entity == null)
            return null;
        FilmDto dto = modelMapper.map(entity,FilmDto.class);
        List<CategoryFilmEntity> tempCategoryFilmEntity = categoryFilmRepository.findByFilmId(dto.getId());
        if(tempCategoryFilmEntity.size()>0) {
            List<CategoryDto> genres = new ArrayList<>();
            for(CategoryFilmEntity categoryFilmEntity: tempCategoryFilmEntity){
                CategoryDto genre = categoryConverter.entityCategoryToDto(categoryRepository.findById(categoryFilmEntity.getCategoryId()).orElse(null));
                if (genre != null)
                    genres.add(genre);
            }
            dto.setGenre(genres);
        }
        else{
            dto.setGenre(null);
        }
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
        if(dto.getPoster() == null)
            newEntity.setPoster(entity.getPoster());
        newEntity.setCreateDate(entity.getCreateDate());
        newEntity.setCreateBy(entity.getCreateBy());
        return newEntity;
    }
}
