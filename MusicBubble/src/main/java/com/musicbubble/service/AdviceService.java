package com.musicbubble.service;

import com.musicbubble.model.CommentEntity;
import com.musicbubble.model.ContainEntity;
import com.musicbubble.model.PreferEntity;
import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.*;
import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.base.MyService;
import com.musicbubble.service.base.ResourceService;
import com.musicbubble.tools.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2016/12/17.
 */
@Service
public class AdviceService extends MyService {

    private static Logger logger = Logger.getLogger(AdviceService.class);

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ContainRepository containRepository;

    @Autowired
    private PreferRepository preferRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResourceService resourceService;

    @Transactional
    public boolean likeSongList(int user_id, int list_id) {
        PreferEntity preferEntity = new PreferEntity();
        preferEntity.setUserId(user_id);
        preferEntity.setListId(list_id);
        preferEntity.setPreferTime(new Timestamp(System.currentTimeMillis()));

        preferRepository.save(preferEntity);

        likeRepository.likeSongList(list_id);

        return true;
    }


    @Transactional
    public boolean likeSong(int user_id, int songlist_id, int song_id) {

        int defaultSongListId = accountService.FindDefaultSongList(user_id);

        if (song_id == 0 || defaultSongListId == 0) {
            return false;
        }
        int image_id = containRepository.findImageIdOfSong(songlist_id, song_id);

        ContainEntity entity = new ContainEntity();
        entity.setSongId(song_id);
        entity.setListId(defaultSongListId);
        entity.setImageId(image_id);
        containRepository.save(entity);

        return true;
    }

    @Transactional
    public int Comment(int user_id, int list_id, String content){
        if(content.length() > 65535){
            return 0;
        }
        CommentEntity entity = new CommentEntity();
        entity.setUserId(user_id);
        entity.setListId(list_id);
        entity.setContent(content);
        entity.setCommentTime(new Timestamp(System.currentTimeMillis()));
        entity.setLikes(0);
        entity = commentRepository.saveAndFlush(entity);

        return entity.getCommentId();
    }

    public List<Map<String, Object>> GetComment(int list_id){
        long start = System.currentTimeMillis();
        List<CommentEntity> comments = commentRepository.findByListId(list_id);
        logger.info("running SQL :" + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        List<Map<String, Object>> list = new ArrayList<>();
        for (CommentEntity c : comments){
            Map<String, Object> map = new HashMap<>();
            UserEntity entity = userRepository.findOne(c.getUserId());
            map.put("user",  entity.getUserName());
            map.put("rank", entity.getRank());
            map.put("avator_url", resourceService.GetImgUrlById(entity.getImageId()));
            map.put("content", c.getContent());
            map.put("time", TimeUtil.GetTimeStampString(c.getCommentTime()));
            map.put("likes", c.getLikes());
            list.add(map);
        }
        logger.info("running loop :" +(System.currentTimeMillis() - start));
        return list;
    }
}
