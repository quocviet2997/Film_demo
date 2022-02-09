package com.webfilminfo.demo.converter.impl;

import com.webfilminfo.demo.converter.ICategoryFilmConverter;
import com.webfilminfo.demo.dto.CategoryFilmDto;
import com.webfilminfo.demo.entity.CategoryEntity;
import com.webfilminfo.demo.entity.CategoryFilmEntity;
import com.webfilminfo.demo.entity.FilmEntity;
import com.webfilminfo.demo.entity.UserEntity;
import com.webfilminfo.demo.repository.CategoryRepository;
import com.webfilminfo.demo.repository.FilmRepository;
import com.webfilminfo.demo.service.ICategoryService;
import com.webfilminfo.demo.service.IFilmService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryFilmConverter implements ICategoryFilmConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public CategoryFilmEntity dtoToEntity(CategoryFilmDto dto) {
        return modelMapper.map(dto, CategoryFilmEntity.class);
    }

    @Override
    public CategoryFilmEntity dtoToEntity(CategoryFilmEntity entity, CategoryFilmDto dto) {
        CategoryFilmEntity newEntity = modelMapper.map(dto, CategoryFilmEntity.class);
        return newEntity;
    }

    @Override
    public CategoryFilmDto entityToDto(CategoryFilmEntity entity) {
        CategoryFilmDto categoryFilmDto = modelMapper.map(entity, CategoryFilmDto.class);
        CategoryEntity category = categoryRepository.findById(categoryFilmDto.getCategoryId()).orElse(null);
        FilmEntity film = filmRepository.findById(categoryFilmDto.getFilmId()).orElse(null);
        if(category != null)
            categoryFilmDto.setCategoryName(category.getCategoryName());
        if(film != null)
            categoryFilmDto.setFilmName(film.getTitle());
        return categoryFilmDto;
    }
}
