package com.musicbubble.repository;

import com.musicbubble.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by happyfarmer on 2016/12/3.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserName(String userName);

    @Modifying
    @Query("update UserEntity u set u.experience = u.experience + ?2 where u.userId = ?1")
    int incExperience(int user_id, int exp);

    @Modifying
    @Query("update UserEntity u set u.rank = u.rank + 1 where u.userId = ?1")
    int incRank(int user_id);

    @Query("select u.listId from UserEntity u where u.userId = ?1")
    int findListIdByUserId(int userId);

    @Modifying
    @Query("update UserEntity u set u.lastSignin = ?2 where u.userId = ?1")
    int updateTime(int user_id, Timestamp timestamp);

    @Modifying
    @Query("update UserEntity u set u.listId = ?2 where u.userId = ?1")
    int setListId(int user_id, int list_id);

}
