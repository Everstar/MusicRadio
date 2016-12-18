package com.musicbubble.controller;

import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.SongListService;
import com.musicbubble.tools.DESUtil;
import com.musicbubble.tools.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> signUp(@RequestBody Map<String, String> data) {
        String username = data.get("username").trim();
        String password = data.get("password").trim();
        String sex = data.get("gender").trim();

        System.out.println(username + " " + password + " " + sex);

        Map<String, Object> map = new HashMap<>();
        int user_id = accountService.SignUp(username, password, sex);
        System.out.println("user_id : " + user_id);
        boolean res = user_id != -1;

        if (res) {
            int list_id = songListService.CreateSongList(user_id, "MyFavorite", "");
            System.out.println("list_id" + list_id);
            accountService.SetDefaultSongList(user_id, list_id);
        }
        map.put("result", res);
        HttpStatus status = res ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(map, status);
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
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

        //set expire time;
        String tokenTime = DateTimeUtil.makeExpireTime(3600);

        HttpStatus status = HttpStatus.OK;
        try {
            String encrypted = DESUtil.encrypt(accountService.Exists(username) + "&" + tokenTime, DESUtil.getKey());
            System.out.println("token " + encrypted);

            Cookie cookie = new Cookie("token", encrypted);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(map, status);
    }
}
