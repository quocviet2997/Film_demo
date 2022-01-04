package com.webfilminfo.demo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDto extends BaseDto {
    private Long filmId;
    private Long userId;
    private String comment;
    private Long replyId;
    private String userName;
    private String filmName;
    private List<CommentDto> childComments;
}
