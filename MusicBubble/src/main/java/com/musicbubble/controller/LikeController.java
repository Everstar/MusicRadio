package com.musicbubble.controller;

import com.musicbubble.service.AccountService;
import com.musicbubble.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.function.ObjDoubleConsumer;


/**
 * Created by DELL on 2016/12/17.
 */
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value="/likesonglist",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> likeSonglist(@CookieValue(value = "token", required = true)String token, @RequestBody Map<String,String> data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        int listId=Integer.valueOf(data.get("songlist_id").trim());

        boolean succeed=likeService.songListLiked(user_id,listId);
        if(succeed) {
            return new ResponseEntity<Object>(succeed, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Object>(succeed,HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/likesong",method = RequestMethod.POST,produces = "application/json.charset=UTF-8")
    public ResponseEntity<Object>likeSong(@CookieValue(value = "token",required = true)String token,@RequestBody Map<String,String>data){
        int user_id=accountService.IdentifyUser(token);
        if(user_id==-1)return  new ResponseEntity<Object>(null,HttpStatus.UNAUTHORIZED);

        int songId=Integer.valueOf(data.get("song_id").trim());


    }
}
