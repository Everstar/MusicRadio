package com.musicbubble.repository;

import com.musicbubble.model.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by happyfarmer on 2016/12/11.
 */
public interface FollowRepository extends JpaRepository<FollowEntity, Integer>{

    @Query("select f.followId from FollowEntity f where f.userId = ?1")
    List<Integer> findFollowId(int user_id);

    @Query("select count (*) from FollowEntity f where f.userId = ?1")
    int countFollow(int user_id);

    @Query("select count(*) from FollowEntity  f where  f.userId = ?1 and f.followId = ?2")
    int Exists(int user_id, int follow_id);
}
