package com.musicbubble.service;

import com.musicbubble.model.ImageEntity;
import com.musicbubble.model.SongListEntity;
import com.musicbubble.model.UserEntity;
import com.musicbubble.repository.ImageRepository;
import com.musicbubble.repository.SongListRepository;
import com.musicbubble.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/11.
 */
@Service
public class SongListService extends MyService {
    @Autowired
    private SongListRepository songListRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Map<String, Object>> GetHotList(int page_size) {
        PageRequest request = buildPageRequest(1, page_size);
        Page<SongListEntity> songs = songListRepository.findHotlist(request);
        List<Map<String, Object>> list = fillSongList(songs.getContent());
        return list;
    }

    public List<Map<String, Object>> GetSongListByUserId(int user_id) {
        List<SongListEntity> songs = songListRepository.findByUserId(user_id);
        List<Map<String, Object>> list = fillSongList(songs);
        return list;
    }

    private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
        return new PageRequest(pageNumber - 1, pagzSize, null);
    }

    private List<Map<String, Object>> fillSongList(List<SongListEntity> songs) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SongListEntity s : songs) {
            Map<String, Object> map = new HashMap<>();
            map.put("list_id", s.getListId());
            map.put("songlist_name", s.getListName());
            map.put("author_id", s.getUserId());
            map.put("liked", s.getLikes());
            UserEntity userEntity = userRepository.findOne(s.getUserId());
            ImageEntity imageEntity = imageRepository.findOne(s.getImageId());
            map.put("author", userEntity == null ? null : userEntity.getUserName());
            map.put("img_url", imageEntity == null ? null : imageEntity.getImageUri());
            list.add(map);
        }
        return list;
    }

//    public int NumOfSongListByUserId()
}
