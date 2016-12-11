package com.musicbubble.controller;

import com.musicbubble.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/api/song", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getMusicInfo(@RequestParam("id")String song_id){
        Map<String, String> map = resourceService.GetMusicInfoById(song_id);
        HttpStatus status = HttpStatus.OK;
        if(map == null){
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "/img", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getImgUrl(@RequestParam("id") String img_id){
        String url = resourceService.GetImgUrlById(Integer.parseInt(img_id));
        Map<String, String>map = null;
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(url != null){
            status = HttpStatus.OK;
            map = new HashMap<>();
            map.put("imgUrl", url);
        }
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "api/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> searchMusic(@RequestParam("s") String key){
        return null;
    }

    @RequestMapping(value = "api/lyric", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getMusicLyric(@RequestParam("id")String song_id){
        String map = resourceService.GetMusicLyric(song_id);
        HttpStatus status = HttpStatus.OK;
        if(map == null){
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Object>(map, status);
    }

}
