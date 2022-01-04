package com.webfilminfo.demo.converter;

import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.entity.UserEntity;

public interface IUserConverter {
    UserDto entiUserToDto (UserEntity entity);
    UserEntity dtoUserToEntity (UserDto dto);
    UserEntity dtoUserToEntity (UserEntity entity, UserDto dto);
}
