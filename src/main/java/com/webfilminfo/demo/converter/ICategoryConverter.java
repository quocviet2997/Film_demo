package com.webfilminfo.demo.converter;

import com.webfilminfo.demo.dto.CategoryDto;
import com.webfilminfo.demo.entity.CategoryEntity;

public interface ICategoryConverter {
    CategoryEntity dtoCategoryToEntity(CategoryDto categoryDto);
    CategoryDto entityCategoryToDto(CategoryEntity categoryEntity);
    CategoryEntity dtoCategoryToEntity(CategoryEntity categoryEntity, CategoryDto categoryDto);
}
