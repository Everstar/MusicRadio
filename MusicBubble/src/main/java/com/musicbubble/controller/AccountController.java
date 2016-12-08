package com.musicbubble.controller;

import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by happyfarmer on 2016/12/6.
 */
@Controller
public class AccountController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public boolean validateAccount(@RequestParam("id") String id){
        boolean exists = userRepository.exists(Integer.parseInt(id));
        return exists;
    }



}
