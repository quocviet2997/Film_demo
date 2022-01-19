package com.webfilminfo.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="comment")
@Data
public class CommentEntity extends BaseEntity {
    @Column(name="filmId", nullable = false)
    private Integer filmId;
    @Column(name = "userId", nullable = false)
    private Integer userId;
    @Column(name = "replyId")
    private Long replyId;
    @Column(columnDefinition = "TEXT", name = "comment")
    private String comment;
}
