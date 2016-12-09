package com.musicbubble.repository;

import com.musicbubble.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by happyfarmer on 2016/12/3.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{


    UserEntity findByUserName(String userName);

    @Query("select u.passwd from UserEntity u where u.userName = ?1")
    String findPasswdByUserName(String userName);

    @Modifying
    @Query("update UserEntity u set u.experience = u.experience + ?2 where u.userName = ?1")
    int incExperience(String userName, int exp);

}
