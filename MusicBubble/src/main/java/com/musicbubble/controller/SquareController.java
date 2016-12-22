package com.musicbubble.controller;

import com.musicbubble.service.FollowService;
import com.musicbubble.service.SquareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 12/20/2016.
 */
@RestController
public class SquareController {

    @Autowired
    private SquareService squareService;

    @RequestMapping(value = "/square", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> showSquare(){
        List<Map<String, Object>> list = squareService.GetSquare();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
