package com.musicbubble.controller;

import com.musicbubble.service.AccountService;
import com.musicbubble.service.SongListService;
import com.musicbubble.tools.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
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

    @RequestMapping(value = "/newlist",  method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> addSongList(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, String>data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        String song_name = data.get("songlist_name").trim();
        String desc = data.get("description").trim();
        int list_id = songListService.CreateSongList(user_id, song_name, desc);
        Map<String, Object> map = new HashMap<>();
        map.put("songlist_id", list_id);
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/deletelist",  method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> removeSongList(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Integer>data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        int list_id = data.get("id");
        boolean res = songListService.DeleteSongList(list_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "/changelist",  method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> changeSongList(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Object>data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        Integer list_id = (Integer)data.get("songlist_id");
        String song_name = ((String)data.get("songlist_name")).trim();
        String desc = ((String)data.get("description")).trim();

        boolean res = songListService.ChangeSongListInfo(list_id, song_name, desc);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<Object>(map, status);
    }

    //带有上传文件
    @RequestMapping(value = "/addsong",  method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> addSong(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Object>data){

    }

    @RequestMapping(value = "/removesong",  method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> removeSong(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Integer>data){
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        int list_id = data.get("songlist_id");
        int song_id = data.get("song_id");

        boolean res = songListService.DeleteSong(list_id, song_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "/changesong",  method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> changeSong(@CookieValue(value = "token", required = true) String token, @RequestBody Map<String, Object>data){
        
    }


}
