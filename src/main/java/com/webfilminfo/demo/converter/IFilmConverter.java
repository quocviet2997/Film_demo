package com.webfilminfo.demo.converter;

import com.webfilminfo.demo.entity.FilmEntity;
import com.webfilminfo.demo.dto.FilmDto;

public interface IFilmConverter {
    FilmDto entiFilmToDto (FilmEntity entity);
    FilmEntity dtoFilmToEntity (FilmDto dto);
    FilmEntity dtoFilmToEntity (FilmEntity entity, FilmDto dto);
}
