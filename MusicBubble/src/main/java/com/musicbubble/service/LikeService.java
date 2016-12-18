package com.musicbubble.service;

import com.musicbubble.model.ContainEntity;
import com.musicbubble.model.PreferEntity;
import com.musicbubble.repository.*;
import com.musicbubble.service.base.MyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

/**
 * Created by DELL on 2016/12/17.
 */
public class LikeService extends MyService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContainRepository containRepository;

    @Autowired
    private PreferRepository preferRepository;

    public boolean likeSongList(int user_id, int list_id){

        PreferEntity preferEntity=new PreferEntity();
        preferEntity.setUserId(user_id);
        preferEntity.setListId(list_id);
        preferEntity.setPreferTime(new Timestamp(System.currentTimeMillis()));

        preferRepository.save(preferEntity);


        return likeRepository.likeSongList(list_id);
    }

    /*public boolean dislikeSongList(int user_id, int list_id){



    }*/

    public boolean likeSong(int user_id,int song_id){

        int defaultSongListId=userRepository.findListIdByUserId(user_id);

        if(song_id == 0 || defaultSongListId == 0)
            return false;
        ContainEntity entity = new ContainEntity();
        entity.setSongId(song_id);
        entity.setListId(defaultSongListId);
        entity.setImageId(1);
        containRepository.save(entity);
        return true;
    }




}
