package com.webfilminfo.demo.converter.impl;

import com.webfilminfo.demo.converter.IUserConverter;
import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements IUserConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto entiUserToDto(UserEntity entity) {
        if(entity == null)
            return null;
        return modelMapper.map(entity, UserDto.class);
    }

    @Override
    public UserEntity dtoUserToEntity(UserDto dto) {
        if(dto == null)
            return null;
        return modelMapper.map(dto, UserEntity.class);
    }

    @Override
    public UserEntity dtoUserToEntity(UserEntity entity, UserDto dto) {
        if(entity == null || dto == null)
            return null;
        UserEntity newEntity = modelMapper.map(dto, UserEntity.class);
        newEntity.setCreateDate(entity.getCreateDate());
        newEntity.setCreateBy(entity.getCreateBy());
        return newEntity;
    }
}
