package com.musicbubble.repository;

import com.musicbubble.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by happyfarmer on 12/15/2016.
 */

public interface CommentRepository extends JpaRepository<CommentEntity, Integer>{

    @Query("select c from CommentEntity c where c.userId = ?1 and c.commentTime > ?2")
    List<CommentEntity> findCommentByUserIdAndTime(int user_id, Timestamp timestamp);


    List<CommentEntity> findByListId(int list_id);
}
