package com.webfilminfo.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="comment")
@Data
public class CommentEntity extends BaseEntity {
    @JoinColumn(name="filmId", insertable = false, updatable = false)
    @ManyToOne(targetEntity = FilmEntity.class, fetch = FetchType.LAZY)
    private FilmEntity film;
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    private UserEntity user;
    @Column(name="filmId", nullable = false)
    private Long filmId;
    @Column(name = "userId", nullable = false)
    private Long userId;
    @JoinColumn(name = "replyId", insertable = false, updatable = false)
    @OneToOne(targetEntity = CommentEntity.class, fetch = FetchType.LAZY)
    private CommentEntity reply;
    @Column(name = "replyId")
    private Long replyId;
    @Column(columnDefinition = "TEXT", name = "comment")
    private String comment;

    public void setFilm(FilmEntity film) {
        setFilmId(user.getId());
        this.film = film;
    }

    public void setUser(UserEntity user) {
        setUserId(user.getId());
        this.user = user;
    }

    public void setReply(CommentEntity reply) {
        setReplyId(reply.getId());
        this.reply = reply;
    }
}
