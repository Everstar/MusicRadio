package com.musicbubble.controller;

import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by DELL on 2016/12/17.
 */
@RestController
public class AdviceController {
    @Autowired
    private AdviceService adviceService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/likesonglist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> likeSonglist(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Integer> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        int list_id = data.get("songlist_id");

        boolean succeed = adviceService.likeSongList(user_id, list_id);

        Map<String, Object> map = new HashMap<>();
        map.put("result", succeed);
        HttpStatus status = succeed ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(map, status);
    }

    @RequestMapping(value = "/likesong", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> likeSong(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Integer> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        int list_id = data.get("songlist_id");
        int song_id = data.get("song_id");

        boolean succeed = adviceService.likeSong(user_id, list_id, song_id);
        Map<String, Object> map = new HashMap<>();
        map.put("result", succeed);
        HttpStatus status = succeed ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(map, status);

    }

    //tested
    @RequestMapping(value = "/addcomment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> addComment(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Object> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        int list_id = (Integer) data.get("songlist_id");
        String content = (String) data.get("content");

        int comment_id = adviceService.Comment(user_id, list_id, content);
        Map<String, Object> map = new HashMap<>();
        map.put("comment_id", comment_id);
        HttpStatus status = comment_id != 0 ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
        return new ResponseEntity<>(map, status);
    }

    //tested
    @RequestMapping(value = "/comment", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getComment(@RequestParam("id") Integer list_id){
        List<Map<String, Object>> list = adviceService.GetComment(list_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
