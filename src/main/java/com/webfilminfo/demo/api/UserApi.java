package com.webfilminfo.demo.api;

import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api-user")
public class UserApi {
    @Autowired
    private IUserService userService;

    @GetMapping
    ResponseEntity<ResponseObject> getUser(@RequestParam(name="page", required=false) Integer page,
                                              @RequestParam(name="limit", required=false) Integer limit){
        return userService.findAll(page, limit);
    }

    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertUser(@RequestBody UserDto dto){
        return userService.save(dto);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody UserDto dto, @PathVariable Long id){
        dto.setId(id);
        return userService.save(dto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id){
        return userService.delete(id);
    }
}