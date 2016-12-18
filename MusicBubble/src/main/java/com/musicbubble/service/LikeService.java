package com.musicbubble.service;

import com.musicbubble.model.PreferEntity;
import com.musicbubble.repository.LikeRepository;
import com.musicbubble.repository.PreferRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

/**
 * Created by DELL on 2016/12/17.
 */
public class LikeService extends MyService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PreferRepository preferRepository;

    public boolean songListLiked(int userId, int listId){

        PreferEntity preferEntity=new PreferEntity();
        preferEntity.setUserId(userId);
        preferEntity.setListId(listId);
        preferEntity.setPreferTime(new Timestamp(System.currentTimeMillis()));

        preferRepository.save(preferEntity);


        return likeRepository.songListLiked(listId);
    }




}
