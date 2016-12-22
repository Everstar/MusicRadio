package com.musicbubble.repository;

import com.musicbubble.model.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by happyfarmer on 12/15/2016.
 */

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

    @Query("select c from CommentEntity c where c.userId = ?1")
    Page<CommentEntity> findCommentByUserIdAndPage(int user_id, Pageable pageable);

    @Query("select c from CommentEntity c order by c.commentTime desc")
    Page<CommentEntity> findNearestComment(Pageable pageable);

    List<CommentEntity> findByListId(int list_id);
}
