package com.musicbubble.controller;

import com.musicbubble.service.AccountService;
import com.musicbubble.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> getOwnInfo(@CookieValue(value = "token", required = true) String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        Map<String, Object> map = followService.GetUserInfo(user_id);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getOthersInfo(@CookieValue(value = "token", required = true) String token, @RequestParam("id") Integer id) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        Map<String, Object> map = followService.GetUserInfo(id);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/moment", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getMomentOfOne(@CookieValue(value = "token", required = true) String token, @RequestParam("id") int id){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = followService.MomentOfOne(id);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/moment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getMomentOfOne(@CookieValue(value = "token", required = true) String token){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = followService.MomentOfFollow(user_id);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

}
