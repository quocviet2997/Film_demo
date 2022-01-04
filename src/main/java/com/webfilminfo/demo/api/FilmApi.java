package com.webfilminfo.demo.api;

import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.service.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api-film")
public class FilmApi {
    @Autowired
    private IFilmService filmService;

    @GetMapping
    ResponseEntity<ResponseObject> getFilm(@RequestParam(name="page", required = false) Integer page,
                                           @RequestParam(name="limit", required = false) Integer limit){
        return filmService.findAll(page, limit);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getFilmDetail(@PathVariable Long id,
                                                 @RequestParam(name="page", required = false) Integer pagecomment,
                                                 @RequestParam(name="limit", required = false) Integer limitcomment){
        return filmService.findAll(id, pagecomment, limitcomment);
    }


    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertFilm(@RequestBody FilmDto dto){
        return filmService.save(dto);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateFilm(@RequestBody FilmDto dto, @PathVariable Long id){
        dto.setId(id);
        return filmService.save(dto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteFilm(@PathVariable Long id){
        return filmService.delete(id);
    }
}
