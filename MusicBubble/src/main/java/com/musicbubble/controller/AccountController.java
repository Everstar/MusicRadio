package com.musicbubble.controller;

import com.musicbubble.service.AccountService;
import com.musicbubble.service.SongListService;
import com.musicbubble.tools.DESUtil;
import com.musicbubble.tools.DateTimeUtil;
import com.musicbubble.tools.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/6.
 */
@RestController
public class AccountController implements Serializable {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SongListService songListService;

    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> validateAccount(@RequestParam("id") String id) {
        Map<String, Object> map = new HashMap<>();
        boolean exists = accountService.Exists(id) != -1;
        map.put("result", exists);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> signUp(@RequestBody Map<String, String> data) {
        String username = data.get("username").trim();
        String password = data.get("password").trim();
        String sex = data.get("gender").trim();
        System.out.println(username + " " + password + " " + sex);
        Map<String, Object> map = new HashMap<>();
        int user_id = accountService.SignUp(username, password, sex);
        boolean res = user_id != -1;
        if (res) {
            Integer list_id = songListService.CreateSongList(user_id, "我喜爱的歌曲", "");
            accountService.SetDefaultSongList(user_id, list_id);
        }
        map.put("result", res);
        HttpStatus status = res ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> signIn(@RequestBody Map<String, String> data) {
        String username = data.get("username").trim();
        String password = data.get("password").trim();
        System.out.println(username + " " + password);
        Map<String, Object> map = new HashMap<>();
        boolean res = accountService.SignIn(username, password);
        map.put("result", res);
        if (!res) {
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }

        //set expire time;
        String tokenTime = DateTimeUtil.makeExpireTime(7 * 24 * 3600);

        HttpStatus status = HttpStatus.OK;
        try {
            String encrypted = DESUtil.encrypt(accountService.Exists(username) + '|' + tokenTime, DESUtil.getKey());
            map.put("token", encrypted);

            System.out.println("token " + encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Object>(map, status);
    }
}
