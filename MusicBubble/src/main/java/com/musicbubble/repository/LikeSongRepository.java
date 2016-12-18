package com.musicbubble.repository;

import com.musicbubble.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by DELL on 2016/12/18.
 */
public interface LikeSongRepository extends JpaRepository<UserEntity,Integer> {
    //@Query("select u.listId  from UserEntity u where u.userId=?1")


}
