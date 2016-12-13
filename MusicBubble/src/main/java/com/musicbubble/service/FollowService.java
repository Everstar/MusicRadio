package com.musicbubble.service;

import com.musicbubble.model.ImageEntity;
import com.musicbubble.model.SongListEntity;
import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.FollowRepository;
import com.musicbubble.repository.ImageRepository;
import com.musicbubble.repository.UserRepository;
import com.musicbubble.tools.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/11.
 */
@Service
public class FollowService extends MyService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SongListService songListService;

    public List<Map<String, Object>> GetFollows(int user_id) {
        List<Integer> followsList = followRepository.findFollowId(user_id);
        List<Map<String, Object>>list = new ArrayList<>();
        for (Integer f_id : followsList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", f_id);

            UserEntity entity = userRepository.findOne(f_id);
            assert entity != null;
            map.put("username", entity.getUserName());

            ImageEntity imageEntity = imageRepository.findOne(entity.getImageId());
            assert imageEntity != null;
            map.put("avator_url", imageEntity.getImageUri());

            list.add(map);
        }
        return list;
    }

    //part
    public Map<String, Object> GetUserInfo(int user_id){
        Map<String, Object> map = null;
        UserEntity userEntity = userRepository.findOne(user_id);
        if(userEntity!=null){
            map.put("id", userEntity.getUserId());
            map.put("username", userEntity.getUserName());
            map.put("level", userEntity.getRank());
            map.put("gender", userEntity.getSex());
            map.put("exp", userEntity.getExperience());

            ImageEntity imageEntity = imageRepository.findOne(userEntity.getImageId());
            assert imageEntity != null;
            map.put("avator_url", imageEntity.getImageUri());

            map.put("exp_max", CommonUtil.calExp(userEntity.getRank()));


        }
        return map;
    }
}
