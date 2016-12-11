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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}
