package com.musicbubble.controller;

import com.musicbubble.service.AccountService;
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

    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> validateAccount(@RequestParam("id") String id) {
        Map<String, Object> map = new HashMap<>();
        boolean exists = accountService.Exists(id);
        map.put("result", exists);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> signUp(@RequestBody String username, @RequestBody String password, @RequestBody String sex) {
        Map<String, Object> map = new HashMap<>();
        boolean res = accountService.SignUp(username, password, sex);
        map.put("result", res);
        HttpStatus status = res ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> signIn(@RequestBody String username, @RequestBody String password) {
        Map<String, Object> map = new HashMap<>();
        boolean res = accountService.SignIn(username, password);
        map.put("result", res);
        if (!res) {
            return new ResponseEntity<Object>(map, HttpStatus.UNAUTHORIZED);
        }

        //set user token;
        String tokenId = Encrypt.SHA512(username);
        //set expire time;
        String tokenTime = DateTimeUtil.makeExpireTime(7 * 24 * 3600);

        HttpStatus status = HttpStatus.OK;
        try {
            String encrypted = DESUtil.encrypt(tokenId + '|' + tokenTime, DESUtil.getKey());
            map.put("token", encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }finally {
            return new ResponseEntity<Object>(map, HttpStatus.OK);
        }
    }
}
