package com.musicbubble.controller;

import com.musicbubble.service.AccountService;
import com.musicbubble.service.SongListService;
import com.musicbubble.tools.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@RestController
public class MusicListController implements Serializable {
    @Autowired
    private SongListService songListService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/hotlist", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getHotList(@RequestParam(value = "num", defaultValue = "10") int num) {
        List<Map<String, Object>> list = songListService.GetHotList(num);
        HttpStatus status = HttpStatus.OK;
        if (list.isEmpty()) status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<Object>(list, status);
    }

    @RequestMapping(value = "/songlist", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> getOwnSongLists(@CookieValue(value = "token", required = true) String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = songListService.GetSongListByUserId(user_id);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/songlist", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getFollowSongLists(@CookieValue(value = "token", required = true) String token, @RequestParam("id") int follow_id) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = songListService.GetSongListByUserId(follow_id);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }
}
