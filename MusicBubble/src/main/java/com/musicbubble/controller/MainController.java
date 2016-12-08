package com.musicbubble.controller;

import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by happyfarmer on 2016/12/3.
 */

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/users/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public UserEntity getUserPasswd(@RequestParam int id){
        UserEntity userEntity = userRepository.findOne(id);
        return userEntity;
    }
}
