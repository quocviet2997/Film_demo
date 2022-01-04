package com.webfilminfo.demo.api;

import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api-comment")
public class CommentApi {
    @Autowired
    private ICommentService commentService;

    @GetMapping
    ResponseEntity<ResponseObject> getComment(@RequestParam(name="page", required = false) Integer page,
                                              @RequestParam(name="limit", required = false) Integer limit){
        return commentService.findAllWithReplyIdIsNull(page, limit);
    }

    @GetMapping("/all")
    ResponseEntity<ResponseObject> getCommentAll(@RequestParam(name="page", required = false) Integer page,
                                              @RequestParam(name="limit", required = false) Integer limit){
        return commentService.findAll(page, limit);
    }

    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertComment(@RequestBody CommentDto dto){
        return commentService.save(dto);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getComment(@PathVariable Long id){
        return commentService.findCommentById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateComment(@RequestBody CommentDto dto, @PathVariable Long id){
        dto.setId(id);
        return commentService.save(dto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteComment(@PathVariable Long id){
        return commentService.delete(id);
    }
}
