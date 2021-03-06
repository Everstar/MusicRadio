package com.musicbubble.controller;

import com.musicbubble.service.SongListService;
import com.musicbubble.service.base.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@RestController
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/api/song", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getMusicInfo(@RequestParam("id")String song_id){
        Map<String, String> map = resourceService.GetMusicInfoById(song_id);
        HttpStatus status = HttpStatus.OK;
        if(map == null){
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(map, status);
    }


    @RequestMapping(value = "api/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> searchMusic(@RequestParam("key") String keys, @RequestParam("num") int num) {
        System.out.println(keys + " " + num);
        List<Map<String, Object>> list = resourceService.SearchMusic(keys, num, 1);
        HttpStatus status = HttpStatus.OK;
        if (list == null || list.isEmpty()) status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<Object>(list, status);
    }

    @RequestMapping(value = "api/lyric", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> getMusicLyric(@RequestParam("id")String song_id){
        Map<String, String> map = resourceService.GetMusicLyric(song_id);
        HttpStatus status = HttpStatus.OK;
        if(map == null){
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Object>(map, status);
    }

    @RequestMapping(value = "/img", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
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


}
