package com.musicbubble.controller;

import com.musicbubble.service.recommend.RecommendService;
import com.musicbubble.service.RecordService;
import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.recommend.SongMes;
import com.musicbubble.service.recommend.UserMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * Created by DELL on 2016/12/19.
 */
@RestController
public class RecommendController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecommendService recommendService;

    @RequestMapping(value = "/api/recommendSong", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> recommendSong(@CookieValue(value = "token", required = true) String token, @RequestParam("data") int data) {
        //cookie验证
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        //得到要求推荐的用户特征向量
        List<Integer> user_fea = recordService.getOneUserStyle(user_id);

        //得到备选歌曲的特征向量
        List<String> songStyle = recommendService.getRangeOfSongById(100 * (data - 1) + 1, 100 * data);

        //将备选歌曲的特征向量存到用于推荐算法的数据结构中
        SongMes[] songMes = recommendService.getSongVec(songStyle, data);

        //采用K临近算法得到推荐序列
        List<Integer> recommendList = recommendService.kNearer_song(user_fea, songMes);

        return new ResponseEntity<>(recommendList, HttpStatus.OK);

    }

    @RequestMapping(value = "/api/recommendUser", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Object> recommendUser(@CookieValue(value = "token", required = true) String token, @RequestParam("data") int data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == 0) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        //得到要求推荐的用户的特征向量
        List<Integer> user_fea = recordService.getOneUserStyle(user_id);

        //得到其他备选用户的特征向量
        List<List<Integer>> other_fea = recordService.getRangeUserStyle(100 * (data - 1) + 1, 100 * data);

        //将其他备选用户特征向量存储于用于推荐算法中的数据结构中
        UserMes[] userMes = recommendService.getUserVec(other_fea, data, user_id);

        //通过k临近算法得到推荐用户
        List<Integer> recommandList = recommendService.kNearer_user(user_fea, userMes);

        return new ResponseEntity<>(recommandList, HttpStatus.OK);
    }

//    @RequestMapping(value = "/testMartix", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public ResponseEntity<Object> testMartix() {
//
//        String song_style1 = "101010101010101010101";
//        String song_style2 = "101101100000100000000";
//        String song_style3 = "100000000000000000000";
//        String song_style4 = "111111111111111111111";
//        String song_style5 = "000001110000111000101";
//        char[] song1 = song_style1.toCharArray();
//        char[] song2 = song_style2.toCharArray();
//        char[] song3 = song_style3.toCharArray();
//        char[] song4 = song_style4.toCharArray();
//        char[] song5 = song_style5.toCharArray();
//
//
//        double[] song_vec1 = new double[song1.length];
//        double[] song_vec2 = new double[song1.length];
//        double[] song_vec3 = new double[song1.length];
//        double[] song_vec4 = new double[song1.length];
//        double[] song_vec5 = new double[song1.length];
//
//        for (int j = 0; j < song1.length; j++) {
//            song_vec1[j] = song1[j] - '0';
//            song_vec2[j] = song2[j] - '0';
//            song_vec3[j] = song3[j] - '0';
//            song_vec4[j] = song4[j] - '0';
//            song_vec5[j] = song5[j] - '0';
//        }
//
//
//        /*double[] songlist_vec1={1.0,1.0,0.0,1.0,0.0};
//        double[] songlist_vec2={4.0,2.0,0.0,0.0,1.0};
//        double[] songlist_vec3={1.0,0.0,0.0,1.0,0.0};
//        double[] songlist_vec4={6.0,0.0,0.0,3.0,1.0};
//        double[] songlist_vec5={8.0,2.0,3.0,6.0,0.0};*/
//
//       /* SongListMes[] songListMes1=new SongListMes[1];
//        songListMes1[0] =new SongListMes();
//        songListMes1[0].setSonglist_feaVec(songlist_vec5);
//        songListMes1[0].setSonglist_id(1);*/
//
//        SongMes[] songMes = new SongMes[5];
//
//        songMes[0] = new SongMes();
//        songMes[1] = new SongMes();
//        songMes[2] = new SongMes();
//        songMes[3] = new SongMes();
//        songMes[4] = new SongMes();
//
//        songMes[0].setSong_id(1);
//        songMes[1].setSong_id(2);
//        songMes[2].setSong_id(3);
//        songMes[3].setSong_id(4);
//        songMes[4].setSong_id(5);
//
//        songMes[0].setSong_feaVec(song_vec1);
//        songMes[1].setSong_feaVec(song_vec2);
//        songMes[2].setSong_feaVec(song_vec3);
//        songMes[3].setSong_feaVec(song_vec4);
//        songMes[4].setSong_feaVec(song_vec5);
//
//        List<Integer> list = new ArrayList<>();
//
//        list.add(1);
//        list.add(0);
//        list.add(2);
//        list.add(1);
//        list.add(6);
//        list.add(0);
//        list.add(0);
//        list.add(0);
//        list.add(3);
//        list.add(5);
//        list.add(0);
//        list.add(0);
//        list.add(6);
//        list.add(10);
//        list.add(0);
//        list.add(12);
//        list.add(9);
//        list.add(0);
//        list.add(0);
//        list.add(1);
//        list.add(11);
//
//        //Matrix m1=new Matrix(songlist_vec1,songlist_vec1.length);
//
//        //double module=getModuleofVec(m1);
//        List<Integer> recommendList = recommendService.kNearer_song(list, songMes);
//
//        //double[] result=kNearer(list,songListMes1);
//
//        return new ResponseEntity<Object>(recommendList, HttpStatus.OK);
//
//
//    }
//
//    // 100 ( i - 1 ) + 1~~~100 i

}
