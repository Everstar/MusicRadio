package com.musicbubble.controller;

import com.musicbubble.model.DanmuEntity;
import com.musicbubble.repository.DanmuRepository;
import com.musicbubble.service.DanmuService;
import com.musicbubble.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 5/6/2017.
 */
@RestController
public class SongController {
    @Autowired
    private DanmuService danmuService;
    @Autowired
    private SongService songService;

    @RequestMapping(value = "/api/danmu", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> getDanmus(@RequestParam("id") int id){
        List<Map<String, Object>> danmus = danmuService.GetDanmuBySongId(id);
        songService.incPlayedTimes(id);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(danmus, status);
    }
}
