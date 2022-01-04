package com.webfilminfo.demo.api;

import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api-login")
public class LoginApi {
    @Autowired
    private IUserService userService;

    @PostMapping
    ResponseEntity<ResponseObject> login(@RequestBody UserDto user){
        return userService.login(user);
    }

    @PostMapping("/new")
    ResponseEntity<ResponseObject> register(@RequestBody UserDto user){
        return userService.save(user);
    }
}
