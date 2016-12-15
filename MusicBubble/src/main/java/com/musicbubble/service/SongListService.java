package com.musicbubble.service;

import com.musicbubble.model.*;
import com.musicbubble.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ContainRepository containRepository;

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

    public int NumOfSongListByUserId(int user_id) {
        int num = songListRepository.countList(user_id);
        return num;
    }

    public int NumOfLikeListByUserId(int user_id) {
        int num = 0;
        List<SongListEntity> songs = songListRepository.findByUserId(user_id);
        for (SongListEntity s : songs) {
            num += s.getLikes();
        }
        return num;
    }

    public String GetSongListNameById(int list_id) {
        SongListEntity listEntity = songListRepository.findOne(list_id);
        return listEntity == null ? null : listEntity.getListName();
    }

    public List<SongListEntity> GetSongListByUserIdAndTime(int user_id , Timestamp s){
        List<SongListEntity> list = songListRepository.findSongListByUserIdAndTime(user_id, s);
        return list;
    }

    public List<CommentEntity> GetCommentByUserIdAndTime(int user_id , Timestamp s){
        List<CommentEntity> list = commentRepository.findCommentByUserIdAndTime(user_id, s);
        return list;
    }

    public int CreateSongList(int user_id, String song_name, String summary) {
        SongListEntity entity = new SongListEntity();
        entity.setListName(song_name);
        entity.setProfile(summary);
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        entity.setUserId(user_id);
        entity.setImageId(0);
        entity = songListRepository.save(entity);
        return entity.getListId();
    }

    public boolean DeleteSongList(int list_id){
        songListRepository.delete(list_id);
        return true;
    }

    public boolean ChangeSongListInfo(int list_id, String name, String desc){
        int t = songListRepository.updateListInfo(list_id, name, desc);
        return t == 1;
    }

    public boolean DeleteSong(int list_id, int song_id){
        ContainEntityPK containEntityPK = new ContainEntityPK();
        containEntityPK.setListId(list_id);
        containEntityPK.setSongId(song_id);
        containRepository.delete(containEntityPK);
        return true;
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

}
