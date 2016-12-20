package com.musicbubble.controller;

import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.ObjDoubleConsumer;


/**
 * Created by DELL on 2016/12/17.
 */

class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private AccountService accountService;

    //返回的response需要确定
    @RequestMapping(value="/likesonglist",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> likeSonglist(@CookieValue(value = "token", required = true)String token, @RequestBody Map<String,String> data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        int list_id=Integer.valueOf(data.get("songlist_id").trim());

        boolean succeed=likeService.likeSongList(user_id,list_id);
        if(succeed) {
            return new ResponseEntity<Object>(succeed, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Object>(succeed,HttpStatus.BAD_REQUEST);
        }

    }

   /* @RequestMapping(value = "/dislikesonglist",method = RequestMethod.POST,produces ="application/json;charset=UTF-8" )
    public ResponseEntity<Object> dislikeSonglist(@CookieValue(value="token",required = true)String token, @RequestBody Map<String,String>data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        int list_id=Integer.valueOf(data.get("songlist_id").trim());
    }*/

    //返回的response需要确定
    @RequestMapping(value = "/likesong",method = RequestMethod.POST,produces = "application/json.charset=UTF-8")
    public ResponseEntity<Object>likeSong(@CookieValue(value = "token",required = true)String token,@RequestBody Map<String,String>data){
        int user_id=accountService.IdentifyUser(token);
        if(user_id==-1)return  new ResponseEntity<Object>(null,HttpStatus.UNAUTHORIZED);

        int song_id=Integer.valueOf(data.get("song_id").trim());

        boolean succeed=likeService.likeSong(user_id,song_id);

        if(succeed){
            return  new ResponseEntity<Object>(succeed,HttpStatus.OK);
        }
        else{
            return  new ResponseEntity<Object>(succeed,HttpStatus.BAD_REQUEST);
        }

    }

   /* @RequestMapping(value="/dislikesong",method = RequestMethod.POST,produces ="application/json.charset=UTF-8" )
    public ResponseEntity<Object>dislikeSong(@CookieValue(value = "token",required = true)String token,@RequestBody Map<String,String>data){
        int user_id=accountService.IdentifyUser(token);
        if(user_id==-1)return  new ResponseEntity<Object>(null,HttpStatus.UNAUTHORIZED);

        int song_id=Integer.valueOf(data.get("song_id").trim());
    }*/
}
