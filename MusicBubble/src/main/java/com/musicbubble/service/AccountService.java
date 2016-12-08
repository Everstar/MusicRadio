package com.musicbubble.service;

import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.musicbubble.service.MyService;

/**
 * Created by happyfarmer on 2016/12/6.
 */

@Service
public class AccountService extends MyService{
    @Autowired
    UserRepository userRepository;

    public boolean Exists(String user_name){
        UserEntity entity = null;
        entity = userRepository.findByUserName(user_name);
        return entity.equals(null) || entity == null;
    }

    public boolean SignUp(String user_name, String passwd){
        UserEntity entity = null;
        userRepository.saveAndFlush()

    }

    public boolean SignIn(String user_name, String passwd){

    }

}
