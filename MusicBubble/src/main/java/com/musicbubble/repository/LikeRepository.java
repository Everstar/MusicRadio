package com.musicbubble.repository;

import com.musicbubble.model.FollowEntity;
import com.musicbubble.model.SongListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by DELL on 2016/12/17.
 */
public interface LikeRepository extends JpaRepository<SongListEntity, Integer> {
    @Modifying
    @Query("update SongListEntity s set s.likes = s.likes + 1 where s.listId = ?1")
    boolean songListLiked(int listId);

}
