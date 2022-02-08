package com.webfilminfo.demo.api;

import com.webfilminfo.demo.constant.Code;
import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.service.IFilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api-film")
public class FilmApi {
    @Autowired
    private IFilmService filmService;

    @Operation(summary = "Get all films", description = "Get all films", tags= {"film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping
    ResponseEntity<ResponseObject> getFilm(@RequestParam(name="page", required = false) Integer page,
                                           @RequestParam(name="limit", required = false) Integer limit,
                                           @RequestParam(name="searchInfo", required = false) String searchInfo){
        return filmService.findAll(page, limit, searchInfo);
    }

    @Operation(summary = "Get film by Id", description = "Get film by Id", tags= {"film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getFilmDetail(@PathVariable Long id,
                                                 @RequestParam(name="page", required = false) Integer pagecomment,
                                                 @RequestParam(name="limit", required = false) Integer limitcomment){
        return filmService.findAll(id, pagecomment, limitcomment);
    }

    @Operation(summary = "Get all films by Genre_Id", description = "Get all films by Genre_Id", tags= {"film"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping("/genre/{id}")
    ResponseEntity<ResponseObject> getFilmByGenre(@PathVariable Long id,
                                                 @RequestParam(name="page", required = false) Integer page,
                                                 @RequestParam(name="limit", required = false) Integer limit){
        return filmService.findAllByGenre(id, page, limit);
    }

    @Operation(summary = "Add a new film", description = "Add a new film", tags = { "film" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.INSERT_SUCCESS, content = { @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FilmDto.class)) }),
            @ApiResponse(responseCode = "405", description = Message.INSERT_FAIL)
    })
    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertFilm(@ModelAttribute FilmDto dto, @RequestParam(value = "file", required = false) MultipartFile file){
        return filmService.save(dto, file);
    }

    @Operation(summary = "Update an existing film", description = "Update an existing film by Id", tags = { "film" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.UPDATE_SUCCESS,
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = FilmDto.class)) }
            ),
            @ApiResponse(responseCode = "400", description = Message.UPDATE_NOT_FOUND_ID),
            @ApiResponse(responseCode = "404", description = Message.UPDATE_NOT_FOUND),
            @ApiResponse(responseCode = "405", description = Message.VALIDATION_EXCEPTION) })
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateFilm(@ModelAttribute FilmDto dto, @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long id){
        dto.setId(id);
        return filmService.save(dto, file);
    }

    @Operation(summary = "Deletes a film", description = "Deletes a film by Id", tags = { "film" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.DELETE_SUCCESS, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FilmDto.class))}),
            @ApiResponse(responseCode = "400", description = Message.DELETE_NOT_FOUND_ID) })
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteFilm(@PathVariable Long id){
        return filmService.delete(id);
    }
}
