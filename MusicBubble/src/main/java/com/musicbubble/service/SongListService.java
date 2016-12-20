package com.musicbubble.service;

import com.musicbubble.model.*;
import com.musicbubble.repository.*;
import com.musicbubble.service.base.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private SongRepository songRepository;
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

    public List<SongListEntity> GetSongListByUserIdAndTime(int user_id, Timestamp s) {
        List<SongListEntity> list = songListRepository.findSongListByUserIdAndTime(user_id, s);
        return list;
    }

    public List<CommentEntity> GetCommentByUserIdAndTime(int user_id, Timestamp s) {
        List<CommentEntity> list = commentRepository.findCommentByUserIdAndTime(user_id, s);
        return list;
    }

    @Transactional
    public int CreateSongList(int user_id, String song_name, String summary) {
        if (song_name.length() > 100 || summary.length() > 65535)
            return 0;
        SongListEntity entity = new SongListEntity();
        entity.setListName(song_name);
        entity.setLikes(0);
        entity.setProfile(summary);
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        entity.setUserId(user_id);
        entity.setImageId(1);
        entity = songListRepository.saveAndFlush(entity);
        return entity.getListId();
    }

    public int GetFavoriteSongList(int user_id) {
        int list_id = songListRepository.findFavoriteSongList(user_id);
        return list_id;
    }

    @Transactional
    public boolean DeleteSongList(int list_id) {
        containRepository.deleteByListId(list_id);
        songListRepository.delete(list_id);
        return true;
    }

    public Map<String, Object> GetSongListInfo(int list_id){
        SongListEntity entity = songListRepository.findOne(list_id);
        if(entity == null)
            return null;
        Map<String, Object> map = new HashMap<>();
        map.put("songlist_name", entity.getListName());
        map.put("description", entity.getProfile());
        map.put("image_id", entity.getImageId());
        return map;
    }

    public boolean AddSongToList(int list_id, int song_id){
        System.out.println("list_id :" + list_id + "|song_id :" + song_id);
        if(song_id == 0 || list_id == 0)
            return false;
        ContainEntity entity = new ContainEntity();
        entity.setSongId(song_id);
        entity.setListId(list_id);
        entity.setImageId(1);
        containRepository.save(entity);
        return true;
    }

    public boolean ChangeSongListInfo(int list_id, String name, String desc, int image_id) {
        int res = songListRepository.updateListInfo(list_id, name, desc, image_id);
        return res == 1;
    }

    @Transactional
    public boolean ChangeSongImage(int list_id, int song_id, int image_id){
        containRepository.updateSongImage(list_id, song_id, image_id);
        return true;
    }

    public int SongExists(int netease_id){
        SongEntity entity = songRepository.findByNeteaseId(netease_id);
        return entity==null ? 0 : entity.getSongId();
    }

    public boolean DeleteSong(int list_id, int song_id) {
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
