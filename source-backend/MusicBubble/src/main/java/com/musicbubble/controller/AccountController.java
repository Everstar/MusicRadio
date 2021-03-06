package com.musicbubble.controller;

import com.musicbubble.service.RecordService;
import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.SongListService;
import com.musicbubble.service.base.ResourceService;
import com.musicbubble.tools.DESUtil;
import com.musicbubble.tools.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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
    @Autowired
    private RecordService recordService;
    @Autowired
    private ResourceService resourceService;

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
        System.out.println("user_id :" + user_id);
        boolean res = user_id != 0;

        if (res) {
            int list_id = songListService.CreateSongList(user_id, "MyFavorite", "");
            System.out.println("list_id :" + list_id);
            accountService.SetDefaultSongList(user_id, list_id);
            recordService.CreateTaste(user_id);
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
        String tokenTime = TimeUtil.makeExpireTime(24 * 3600);

        HttpStatus status = HttpStatus.OK;
        try {
            String encrypted = DESUtil.encrypt(accountService.Exists(username) + "&" + tokenTime, DESUtil.getKey());
            System.out.println("token " + encrypted);

            Cookie cookie = new Cookie("token", encrypted);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 3600);
            HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(map, status);
    }

    @RequestMapping(value = "/signout", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> signOut(@CookieValue("token") String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        boolean res = accountService.SignOut();
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(map, status);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> update(@CookieValue("token") String token,
        @RequestParam("image_file") CommonsMultipartFile image, HttpServletRequest request){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        int image_id = 0;
        HttpStatus status;
        if (image != null
                && image.getSize() > 0
                && image.getContentType().startsWith("image")) {
            //上传图片
            image_id = resourceService.SaveUploadResource(image, "image", null, null);
        }else {
            status = HttpStatus.BAD_REQUEST;
        }
        boolean res = accountService.SetImage(user_id, image_id);
        status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<>(map, status);
    }
}
