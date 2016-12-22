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
public class RecommendService extends MyService {
    @Autowired
    private SongRepository songRepository;


    public List<String> getRangeOfSongById(int pre_id,int fal_id ){
        return songRepository.findRangeOfSongById(pre_id,fal_id);
    }

}
