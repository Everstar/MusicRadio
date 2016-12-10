package com.musicbubble.service;

import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.musicbubble.service.MyService;
import com.musicbubble.tools.Encrypt;

/**
 * Created by happyfarmer on 2016/12/6.
 */

@Service
public class AccountService extends MyService {
    @Autowired
    private UserRepository userRepository;

    public boolean Exists(String user_name) {
        UserEntity entity = null;
        entity = userRepository.findByUserName(user_name);
        return entity != null;
    }

    public boolean SignUp(String user_name, String passwd, String sex) {
        if (user_name.length() > 100 || passwd.length() > 20 || (!sex.equals("M") && !sex.equals("F")))
            return false;
        UserEntity entity = new UserEntity();
        entity.setUserId(0);
        entity.setUserName(user_name);
        entity.setPasswd(Encrypt.SHA512(passwd));
        entity.setRank(1);
        entity.setSex(sex.equals("M") ? "M" : "F");
        entity.setExperience(0);
        userRepository.save(entity);
        return true;
    }

    public boolean SignIn(String user_name, String passwd) {
        String password = userRepository.findPasswdByUserName(user_name);
        if (!password.equals(Encrypt.SHA512(passwd))) {
            System.out.println("password invalid");
            return false;
        }
//        userRepository.incExperience(user_name, 10);

        return true;
    }

}
