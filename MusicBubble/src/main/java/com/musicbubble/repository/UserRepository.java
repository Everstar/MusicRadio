package com.musicbubble.repository;

import com.musicbubble.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by happyfarmer on 2016/12/3.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{


    UserEntity findByUserName(String userName);

    void


}
