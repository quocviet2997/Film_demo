package com.webfilminfo.demo.service.impl;

import com.webfilminfo.demo.constant.Code;
import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.constant.Paging;
import com.webfilminfo.demo.converter.impl.UserConverter;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.entity.CommentEntity;
import com.webfilminfo.demo.entity.UserEntity;
import com.webfilminfo.demo.repository.UserRepository;
import com.webfilminfo.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDto findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return userConverter.entiUserToDto(userEntity);
    }

    @Override
    public ResponseEntity<ResponseObject> findUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        return (userEntity==null)?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1, null)):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, userEntity));
    }

    @Override
    public ResponseEntity<ResponseObject> findAll() {
        List<UserEntity> users = userRepository.findAll();
        if(users.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, 1, null, 1, null)
            );
        }
        List<UserDto> dtos = new ArrayList<>();
        for(UserEntity user: users){
            dtos.add(userConverter.entiUserToDto(user));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.FIND_SUCCESS, 1, null, 1, dtos)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAll(Integer page, Integer limit) {
        if(limit == null){
            limit = Paging.LIMIT_COMMENT;
        }
        if(page == null){
            page = Paging.PAGE_DEFAULT;
        }
        Pageable pageable = PageRequest.of(page-1, limit);
        Integer totalPage = (int)Math.ceil((double)totalItem()/limit);
        List<UserEntity> users = userRepository.findAll(pageable).getContent();
        if(users.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(Code.NOT_FOUND, Message.FIND_NOT_FOUND, totalPage, limit, page, null)
            );
        }
        List<UserDto> dtos = new ArrayList<>();
        for(UserEntity user: users){
            dtos.add(userConverter.entiUserToDto(user));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.FIND_SUCCESS, totalPage, limit, page, dtos)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> save(UserDto userDto) {
        Long id = userDto.getId();
        UserEntity entity = null;
        if(id == null){
            entity = userConverter.dtoUserToEntity(userDto);
            UserEntity temp = userRepository.save(entity);
            return (entity==null)?
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(Code.FAILED, Message.INSERT_FAIL, null)
            ):
                    ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(Code.OK, Message.INSERT_SUCCESS, entity)
            );
        }
        else{
            entity = userRepository.findById(id).orElse(null);
            if(entity != null){
                entity = userConverter.dtoUserToEntity(entity ,userDto);
                UserEntity temp = userRepository.save(entity);
            }
            return (entity==null)?
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject(Code.NOT_FOUND, Message.UPDATE_NOT_FOUND_ID, null)
                    ):
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(Code.OK, Message.UPDATE_SUCCESS, entity)
                    );
        }
    }

    @Override
    public ResponseEntity<ResponseObject> delete(Long id) {
        UserEntity temp = userRepository.findById(id).orElse(null);
        if(temp==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(Code.NOT_FOUND, Message.DELETE_NOT_FOUND_ID, null)
            );
        }
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(Code.OK, Message.DELETE_SUCCESS, temp)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> login(UserDto userDto) {
        if((userDto.getUserName() == null) || (userDto.getPassword() == null))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Failed", "Username or password is invalid", null)
            );
        List<UserEntity> user = userRepository.findByUserNameAndPassword(userDto.getUserName(), userDto.getPassword());
        if(user.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("Failed", "User or password is mismatched", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Login successful", user.get(0))
        );
    }

    @Override
    public Integer totalItem() {
        return (int)userRepository.count();
    }
}
