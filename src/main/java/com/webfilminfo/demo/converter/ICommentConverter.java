package com.webfilminfo.demo.converter;

import com.webfilminfo.demo.dto.CommentDto;
import com.webfilminfo.demo.entity.CommentEntity;

import java.util.List;

public interface ICommentConverter {
    CommentDto entiCommentToDto (CommentEntity entity);
    CommentEntity dtoCommentToEntity (CommentDto dto);
    CommentEntity dtoCommentToEntity (CommentEntity entity, CommentDto dto);
    List<CommentDto> entiListCommentToDto(List<CommentEntity> entities);
}
