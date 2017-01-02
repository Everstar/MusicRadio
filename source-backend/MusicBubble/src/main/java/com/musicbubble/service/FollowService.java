package com.musicbubble.service;

import com.musicbubble.model.*;
import com.musicbubble.repository.FollowRepository;
import com.musicbubble.repository.PreferRepository;
import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.base.MyService;
import com.musicbubble.service.base.ResourceService;
import com.musicbubble.tools.CommonUtil;
import com.musicbubble.tools.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by happyfarmer on 2016/12/11.
 */
@Service
public class FollowService extends MyService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private SongListService songListService;

    @Autowired
    private PreferRepository preferRepository;

    public boolean AddFollows(int user_id, int follow_id) {
        if (user_id == follow_id)
            return false;
        FollowEntity entity = new FollowEntity();
        entity.setUserId(user_id);
        entity.setFollowId(follow_id);
        followRepository.save(entity);
        return true;
    }

    public List<Map<String, Object>> GetFollows(int user_id) {
        List<Integer> followsList = followRepository.findFollowId(user_id);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer f_id : followsList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", f_id);

            map.put("username", accountService.GetUserNameById(f_id));
            map.put("avator_url", resourceService.GetImgUrlById(accountService.GetImage(f_id)));

            list.add(map);
        }
        return list;
    }

    public Map<String, Object> GetUserInfo(int user_id, int other_id) {
        Map<String, Object> map = new HashMap<>();
        UserEntity userEntity = accountService.findOne(other_id == 0 ? user_id : other_id);
        if (userEntity != null) {
            map.put("id", userEntity.getUserId());
            map.put("username", userEntity.getUserName());
            map.put("level", userEntity.getRank());
            map.put("gender", userEntity.getSex());
            map.put("exp", userEntity.getExperience());

            map.put("avator_url", resourceService.GetImgUrlById(accountService.GetImage(userEntity.getUserId())));
            map.put("ctr_songlist", songListService.NumOfSongListByUserId(userEntity.getUserId()));
            map.put("liked_songlist", songListService.NumOfLikeListByUserId(userEntity.getUserId()));
            map.put("friends_num", followRepository.countFollow(userEntity.getUserId()));
            map.put("exp_max", CommonUtil.MaxExp(userEntity.getRank()));

            if (other_id != 0) {
                int cnt = followRepository.Exists(user_id, other_id);
                if (user_id != 0)
                    map.put("followed", cnt > 0);
                else
                    map.put("followed", false);
            }
        }
        return map;
    }

    public List<Map<String, Object>> MomentOfOne(int user_id) {
        final int page_size = 5;
        List<Map<String, Object>> moments = new ArrayList<>();

        List<Map<String, Object>> list = GetPreferPagesByUserId(user_id, page_size);
        moments.addAll(list);
        list = GetCommentPagesByUserId(user_id, page_size);
        moments.addAll(list);
        list = GetCreatePagesByUserId(user_id, page_size);
        moments.addAll(list);

        return moments;
    }

    public List<Map<String, Object>> MomentOfFollow(int user_id) {
        List<Map<String, Object>> moments = new ArrayList<>();
        List<Map<String, Object>> list = GetFollows(user_id);
        for (Map<String, Object> m : list) {
            List<Map<String, Object>> part = MomentOfOne((int) m.get("id"));
            moments.addAll(part);
        }
        return moments;
    }

    public List<Map<String, Object>> GetPreferPagesByUserId(int user_id, int page_size) {
        List<Map<String, Object>> moments = new ArrayList<>();
        PageRequest request = buildPageRequest(1, page_size);
        UserEntity entity = accountService.findOne(user_id);
        String user_name = entity.getUserName();
        String avator_url = resourceService.GetImgUrlById(entity.getImageId());

        List<PreferEntity> prefers = preferRepository.findPreferByUserId(user_id, request).getContent();
        for (PreferEntity p : prefers) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user_id);
            map.put("username", user_name);
            map.put("avator_url", avator_url);
            map.put("type", "like");
            map.put("time", TimeUtil.GetTimeStampString(p.getPreferTime()));
            map.put("songlist_id", p.getListId());
            map.put("songlist_name", songListService.GetSongListNameById(p.getListId()));
            moments.add(map);
        }
        return moments;
    }

    public List<Map<String, Object>> GetCreatePagesByUserId(int user_id, int page_size) {
        List<Map<String, Object>> moments = new ArrayList<>();
        PageRequest request = buildPageRequest(1, page_size);
        UserEntity entity = accountService.findOne(user_id);
        String user_name = entity.getUserName();
        String avator_url = resourceService.GetImgUrlById(entity.getImageId());
        List<SongListEntity> songs = songListService.GetSongListByUserIdAndPage(user_id, request);
        for (SongListEntity s : songs) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user_id);
            map.put("username", user_name);
            map.put("avator_url", avator_url);
            map.put("type", "create");
            map.put("time", TimeUtil.GetTimeStampString(s.getCreateTime()));
            map.put("songlist_id", s.getListId());
            map.put("songlist_name", s.getListName());
            moments.add(map);
        }
        return moments;
    }

    public List<Map<String, Object>> GetCommentPagesByUserId(int user_id, int page_size) {
        List<Map<String, Object>> moments = new ArrayList<>();
        PageRequest request = buildPageRequest(1, page_size);
        UserEntity entity = accountService.findOne(user_id);
        String user_name = entity.getUserName();
        String avator_url = resourceService.GetImgUrlById(entity.getImageId());
        List<CommentEntity> comments = songListService.GetCommentByUserIdAndPage(user_id, request);
        for (CommentEntity c : comments) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user_id);
            map.put("username", user_name);
            map.put("avator_url", avator_url);
            map.put("type", "comment");
            map.put("time", TimeUtil.GetTimeStampString(c.getCommentTime()));
            map.put("songlist_id", c.getListId());
            map.put("songlist_name", songListService.GetSongListNameById(c.getListId()));
            moments.add(map);
        }

        return moments;
    }

    private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
        return new PageRequest(pageNumber - 1, pagzSize, null);
    }
}
