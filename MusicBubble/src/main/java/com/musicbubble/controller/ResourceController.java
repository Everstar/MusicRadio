package com.musicbubble.controller;

import com.musicbubble.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        Map<String, String> map = resourceService.GetImgUrlById(img_id);
        HttpStatus status = HttpStatus.OK;
        if(map == null){
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Object>(map, status);
    }


}
