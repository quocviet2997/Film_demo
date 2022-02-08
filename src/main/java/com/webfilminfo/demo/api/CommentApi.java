package com.webfilminfo.demo.api;

import com.webfilminfo.demo.constant.Message;
import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.dto.FilmDto;
import com.webfilminfo.demo.dto.ResponseObject;
import com.webfilminfo.demo.service.ICommentService;
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
@RequestMapping("/api-comment")
public class CommentApi {
    @Autowired
    private ICommentService commentService;

    @Operation(summary = "Get all comments without reply any comment", description = "Get all comments without reply any comment", tags= {"comment"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping
    ResponseEntity<ResponseObject> getComment(@RequestParam(name="page", required = false) Integer page,
                                              @RequestParam(name="limit", required = false) Integer limit){
        return commentService.findAllWithReplyIdIsNull(page, limit);
    }

    @Operation(summary = "Get all comments", description = "Get all comments", tags= {"comment"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping("/all")
    ResponseEntity<ResponseObject> getCommentAll(@RequestParam(name="page", required = false) Integer page,
                                              @RequestParam(name="limit", required = false) Integer limit){
        return commentService.findAll(page, limit);
    }

    @Operation(summary = "Add a new comment", description = "Add a new comment", tags = { "comment" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.INSERT_SUCCESS, content = { @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class)) }),
            @ApiResponse(responseCode = "405", description = Message.INSERT_FAIL)
    })
    @PostMapping("/new")
    ResponseEntity<ResponseObject> insertComment(@RequestBody CommentDto dto){
        return commentService.save(dto);
    }

    @Operation(summary = "Get comment by Id", description = "Get comment by Id", tags= {"comment"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = Message.FIND_SUCCESS,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = Message.FIND_NOT_FOUND)
    })
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getComment(@PathVariable Long id){
        return commentService.findCommentById(id);
    }

    @Operation(summary = "Update an existing comment", description = "Update an existing comment by Id", tags = { "comment" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.UPDATE_SUCCESS,
                    content =
                            { @Content(mediaType = "application/json", schema = @Schema(implementation = FilmDto.class)) }
            ),
            @ApiResponse(responseCode = "400", description = Message.UPDATE_NOT_FOUND_ID),
            @ApiResponse(responseCode = "404", description = Message.UPDATE_NOT_FOUND),
            @ApiResponse(responseCode = "405", description = Message.VALIDATION_EXCEPTION) })
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateComment(@RequestBody CommentDto dto, @PathVariable Long id){
        dto.setId(id);
        return commentService.save(dto);
    }

    @Operation(summary = "Deletes a comment", description = "Deletes a comment by Id", tags = { "comment" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = Message.DELETE_SUCCESS, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
            @ApiResponse(responseCode = "400", description = Message.DELETE_NOT_FOUND_ID) })
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteComment(@PathVariable Long id){
        return commentService.delete(id);
    }
}
