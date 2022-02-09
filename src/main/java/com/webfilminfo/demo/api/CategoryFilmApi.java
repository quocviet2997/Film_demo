package com.webfilminfo.demo.api;

import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.dto.CategoryFilmDto;
import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.service.ICategoryFilmService;
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
@RequestMapping("/api-category-film")
public class CategoryFilmApi {
    @Autowired
    private ICategoryFilmService categoryFilmService;

    @Operation(summary = "Get all category_films", description = "Get all category_films", tags= {"category_film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping
    ResponseEntity<ResponseObject> getCategory(@RequestParam(name="page", required = false) Integer page, @RequestParam(name="limit", required = false) Integer limit){
        return categoryFilmService.findAll(page, limit);
    }

    @Operation(summary = "Add a new category_film", description = "Add a new category_film", tags = { "category_film" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.INSERT_SUCCESS, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryFilmDto.class)) }),
            @ApiResponse(responseCode = "405", description = Message.INSERT_FAIL)
    })
    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertCategory(@RequestBody CategoryFilmDto categoryFilmDto){
        return categoryFilmService.save(categoryFilmDto);
    }

    @Operation(summary = "Update an existing category_film", description = "Update an existing category_film by Id", tags = { "category_film" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.UPDATE_SUCCESS,
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryFilmDto.class)) }
            ),
            @ApiResponse(responseCode = "400", description = Message.UPDATE_NOT_FOUND_ID),
            @ApiResponse(responseCode = "404", description = Message.UPDATE_NOT_FOUND),
            @ApiResponse(responseCode = "405", description = Message.VALIDATION_EXCEPTION) })
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateCategory(@RequestBody CategoryFilmDto categoryFilmDto, @PathVariable("id") Long categoryFilmId){
        categoryFilmDto.setId(categoryFilmId);
        return categoryFilmService.save(categoryFilmDto);
    }

    @Operation(summary = "Get category_film by Id", description = "Get category_film by Id", tags= {"category_film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getCategoryFilm(@PathVariable Long id){
        return categoryFilmService.findCategoryFilmById(id);
    }

    @Operation(summary = "Deletes a category_film", description = "Deletes a category_film by Id", tags = { "category_film" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.DELETE_SUCCESS, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryFilmDto.class))}),
            @ApiResponse(responseCode = "400", description = Message.DELETE_NOT_FOUND_ID) })
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> updateCategory(@PathVariable("id") Long categoryFilmId){
        return categoryFilmService.delete(categoryFilmId);
    }
}
