package com.musicbubble.service;

import com.musicbubble.repository.SongRepository;
import com.musicbubble.repository.UserRepository;
import com.musicbubble.service.base.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by DELL on 2016/12/21.
 */
@Service
public class RecommandService extends MyService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public List<String> getRangeOfSongById(int pre_id,int fal_id ){
        return songRepository.findRangeOfSongById(pre_id,fal_id);
    }
    /*
    public List<Integer> getUserFecVec(int user_id){
        return userRepository.getUserFecVec(user_id);
    }*/
}
