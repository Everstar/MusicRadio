package com.musicbubble.controller;

import com.musicbubble.service.RecordService;
import com.musicbubble.service.base.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by happyfarmer on 12/22/2016.
 */
@RestController
public class RecordController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RecordService recordService;

    @RequestMapping(value = "/record", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> recordTaste(@CookieValue("token") String token, @RequestBody Map<String, Integer> data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        int song_id = data.get("song_id");
        boolean res = recordService.recordPlayMusic(user_id, song_id);
        Map<String, Object> map = new HashMap<>();
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        map.put("result", res);
        return new ResponseEntity<>(map, status);
    }
}
