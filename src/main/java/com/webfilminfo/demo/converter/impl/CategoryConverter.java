package com.webfilminfo.demo.converter.impl;

import com.webfilminfo.demo.converter.ICategoryConverter;
import com.webfilminfo.demo.dto.CategoryDto;
import com.webfilminfo.demo.entity.CategoryEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter implements ICategoryConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryEntity dtoCategoryToEntity(CategoryDto categoryDto) {
        if(categoryDto == null)
            return null;
        return modelMapper.map(categoryDto, CategoryEntity.class);
    }

    @Override
    public CategoryDto entityCategoryToDto(CategoryEntity categoryEntity) {
        if(categoryEntity == null)
            return null;
        if(modelMapper == null){
            modelMapper = new ModelMapper();
        }
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryEntity dtoCategoryToEntity(CategoryEntity categoryEntity, CategoryDto categoryDto) {
        if(categoryDto == null)
            return null;
        CategoryEntity newEntity = modelMapper.map(categoryDto, CategoryEntity.class);
        newEntity.setCreateDate(categoryEntity.getCreateDate());
        newEntity.setCreateBy(categoryEntity.getCreateBy());
        return newEntity;
    }
}
