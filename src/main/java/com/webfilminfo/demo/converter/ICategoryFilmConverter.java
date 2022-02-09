package com.webfilminfo.demo.converter;

import com.webfilminfo.demo.dto.CategoryFilmDto;
import com.webfilminfo.demo.entity.CategoryFilmEntity;

public interface ICategoryFilmConverter {
    CategoryFilmEntity dtoToEntity(CategoryFilmDto dto);
    CategoryFilmEntity dtoToEntity(CategoryFilmEntity entity, CategoryFilmDto dto);
    CategoryFilmDto entityToDto(CategoryFilmEntity entity);
}
