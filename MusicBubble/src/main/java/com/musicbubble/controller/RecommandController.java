package com.musicbubble.controller;

import com.musicbubble.service.SongListService;
import com.musicbubble.service.base.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2016/12/19.
 */
@Controller
public class RecommandController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SongListService songListService;
    /*@RequestMapping(value="/recommandSonglist",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> recommandSongList(@CookieValue(value = "token",required = true)String token,@RequestBody Map<String,String> data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
       *//*
        1. {"type_1": integer;"type_2": integer; "type_3": integer; .....}
        2.{"songlist_id": integer......}

       *//*
       songListService.


    }*/

    private List<Integer> kNearer(Array userArray){
        /*
          |u1|       | v11 ... ...   v15 |
          |u2|       | v21               |
          |u3|  *    | v31               |
          |u4|       | v41               |
          |u5|      | v51            v55|


         */



        List<Integer> recommandListId=new ArrayList<>();

        return recommandListId;
    }

}
