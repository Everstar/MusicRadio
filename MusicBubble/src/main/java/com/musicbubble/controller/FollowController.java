package com.musicbubble.controller;

import com.musicbubble.service.AccountService;
import com.musicbubble.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/11.
 */
@RestController
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/follow", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getFollows(@CookieValue(value = "token", required = true) String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = followService.GetFollows(user_id);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getUserInfo(@CookieValue(value = "token", required = true) String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        Map<String, Object> map = followService.GetUserInfo(user_id);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
}
