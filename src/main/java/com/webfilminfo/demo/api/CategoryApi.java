package com.webfilminfo.demo.api;

import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.dto.CategoryDto;
import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.service.ICategoryService;
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
@RequestMapping("/api-category")
public class CategoryApi {
    @Autowired
    private ICategoryService categoryService;

    @Operation(summary = "Get all genres", description = "Get all genres", tags= {"genre"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping
    ResponseEntity<ResponseObject> getCategory(@RequestParam(name="page", required = false) Integer page, @RequestParam(name="limit", required = false) Integer limit){
        return categoryService.findAll(page, limit);
    }

    @Operation(summary = "Get all genre by Id", description = "Get all genre by Id", tags= {"genre"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getCategoryFilm(@PathVariable Long id){
        return categoryService.findCategoryById(id);
    }

    @Operation(summary = "Add a new genre", description = "Add a new genre", tags = { "genre" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.INSERT_SUCCESS, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)) }),
            @ApiResponse(responseCode = "405", description = Message.INSERT_FAIL)
    })
    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.save(categoryDto);
    }

    @Operation(summary = "Update an existing genre", description = "Update an existing genre by Id", tags = { "genre" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.UPDATE_SUCCESS,
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class)) }
            ),
            @ApiResponse(responseCode = "400", description = Message.UPDATE_NOT_FOUND_ID),
            @ApiResponse(responseCode = "404", description = Message.UPDATE_NOT_FOUND),
            @ApiResponse(responseCode = "405", description = Message.VALIDATION_EXCEPTION) })
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id){
        categoryDto.setId(id);
        return categoryService.save(categoryDto);
    }

    @Operation(summary = "Deletes a genre", description = "Deletes a genre by Id", tags = { "genre" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.DELETE_SUCCESS, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "400", description = Message.DELETE_NOT_FOUND_ID) })
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> updateCategory(@PathVariable Long id){
        return categoryService.delete(id);
    }
}