package com.musicbubble.controller;

import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/11.
 */

//经过初步测试
@RestController
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/api/follow", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> addFollows(@CookieValue("token") String token, @RequestBody Map<String, Integer> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        Integer follow_id = data.get("user_id");
        boolean res = followService.AddFollows(user_id, follow_id);

        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "/api/follow", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getFollows(@CookieValue("token") String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = followService.GetFollows(user_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/info", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getOwnInfo(@CookieValue("token") String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        Map<String, Object> map = followService.GetUserInfo(user_id, 0);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/info", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getOthersInfo(@CookieValue(value = "token", required = false) String token, @RequestParam("id") Integer id) {
        int user_id = 0;
        if (token != null && !token.equals("")) {
            user_id = accountService.IdentifyUser(token);
            if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> map = followService.GetUserInfo(user_id, id);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //tested
    @RequestMapping(value = "/api/moment", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getMomentOfOne(@CookieValue("token") String token, @RequestParam("id") int id) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = followService.MomentOfOne(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //tested
    @RequestMapping(value = "/api/moment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getMomentOfFollow(@CookieValue("token") String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = followService.MomentOfFollow(user_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
