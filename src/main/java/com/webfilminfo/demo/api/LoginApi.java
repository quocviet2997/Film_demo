package com.webfilminfo.demo.api;

import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api-login")
public class LoginApi {
    @Autowired
    private IUserService userService;

    @Operation(summary = "Login", description = "Login", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.LOGIN_SUCCESS, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "405", description = Message.LOGIN_FAIL)
    })
    @PostMapping
    ResponseEntity<ResponseObject> login(@RequestBody UserDto user){
        return userService.login(user);
    }

    @Operation(summary = "Register", description = "Register", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.REGISTER_SUCCESS, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "405", description = Message.REGISTER_FAIL)
    })
    @PostMapping("/new")
    ResponseEntity<ResponseObject> register(@RequestBody UserDto user){
        return userService.save(user);
    }
}
