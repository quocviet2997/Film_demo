package com.webfilminfo.demo.repository;

import com.webfilminfo.demo.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByFilmId(Long film_id);
    List<CommentEntity> findByReplyId(Long replyId);
    List<CommentEntity> findByReplyIdIsNull();
    Page<CommentEntity> findByReplyIdIsNull(Pageable pageable);
    @Query(value = "SELECT * FROM comment WHERE film_id = ?1 and reply_id is null", nativeQuery = true)
    List<CommentEntity> findByFilmIdAndReplyIdIsNull(Long filmId);
    @Query(value = "SELECT * FROM comment WHERE film_id = ?1 and reply_id is null",
            countQuery = "SELECT * FROM comment WHERE film_id = ?1 and reply_id is null",
            nativeQuery = true)
    Page<CommentEntity> findByFilmIdAndReplyIdIsNull(Long filmId, Pageable pageable);
    long countByReplyIdIsNull();
    @Query(value = "SELECT count(*) FROM comment WHERE film_id = ?1 and reply_id is null", nativeQuery = true)
    Long countByReplyIdIsNullAndFilmId(Long filmId);
}
