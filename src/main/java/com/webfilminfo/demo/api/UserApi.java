package com.webfilminfo.demo.api;

import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.dto.UserDto;
import com.webfilminfo.demo.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all users", description = "Get all users", tags= {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping
    ResponseEntity<ResponseObject> getUser(@RequestParam(name="page", required=false) Integer page,
                                              @RequestParam(name="limit", required=false) Integer limit){
        return userService.findAll(page, limit);
    }

    @Operation(summary = "Add a new user", description = "Add a new user", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.INSERT_SUCCESS, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "405", description = Message.INSERT_FAIL)
    })
    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertUser(@RequestBody UserDto dto){
        return userService.save(dto);
    }

    @Operation(summary = "Get user by Id", description = "Get user by Id", tags= {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @Operation(summary = "Update an existing user", description = "Update an existing user by Id", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.UPDATE_SUCCESS,
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)) }
            ),
            @ApiResponse(responseCode = "400", description = Message.UPDATE_NOT_FOUND_ID),
            @ApiResponse(responseCode = "404", description = Message.UPDATE_NOT_FOUND),
            @ApiResponse(responseCode = "405", description = Message.VALIDATION_EXCEPTION) })
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody UserDto dto, @PathVariable Long id){
        dto.setId(id);
        return userService.save(dto);
    }

    @Operation(summary = "Deletes a user", description = "Deletes a user by Id", tags = { "user" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.DELETE_SUCCESS, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = Message.DELETE_NOT_FOUND_ID) })
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id){
        return userService.delete(id);
    }
}