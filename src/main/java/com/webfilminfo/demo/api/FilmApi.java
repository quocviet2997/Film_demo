package com.webfilminfo.demo.api;

import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.service.IFilmService;
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

    @GetMapping
    ResponseEntity<ResponseObject> getFilm(@RequestParam(name="page", required = false) Integer page,
                                           @RequestParam(name="limit", required = false) Integer limit,
                                           @RequestParam(name="searchInfo", required = false) String searchInfo){
        return filmService.findAll(page, limit, searchInfo);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getFilmDetail(@PathVariable Long id,
                                                 @RequestParam(name="page", required = false) Integer pagecomment,
                                                 @RequestParam(name="limit", required = false) Integer limitcomment){
        return filmService.findAll(id, pagecomment, limitcomment);
    }

    @GetMapping("/genre/{id}")
    ResponseEntity<ResponseObject> getFilmByGenre(@PathVariable Long id,
                                                 @RequestParam(name="page", required = false) Integer page,
                                                 @RequestParam(name="limit", required = false) Integer limit){
        return filmService.findAllByGenre(id, page, limit);
    }

    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertFilm(@ModelAttribute FilmDto dto, @RequestParam(value = "file", required = false) MultipartFile file){
        return filmService.save(dto, file);
    }


    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateFilm(@ModelAttribute FilmDto dto, @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Long id){
        dto.setId(id);
        return filmService.save(dto, file);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteFilm(@PathVariable Long id){
        return filmService.delete(id);
    }
}
