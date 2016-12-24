package com.musicbubble.service;

import com.musicbubble.model.*;
import com.musicbubble.repository.*;
import com.musicbubble.service.base.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private PreferRepository preferRepository;

    public List<Map<String, Object>> GetHotList(int page_size) {
        PageRequest request = buildPageRequest(1, page_size);
        Page<SongListEntity> songs = songListRepository.findHotlist(request);
        List<Map<String, Object>> list = fillSongList(0, songs.getContent());
        return list;
    }

    public List<Map<String, Object>> GetSongListByUserId(int own_id, int follow_id) {
        List<SongListEntity> songs = songListRepository.findByUserId(own_id);
        List<Map<String, Object>> list = fillSongList(follow_id == 0 ? own_id : follow_id, songs);
        return list;
    }

    public List<Map<String, Object>> GetSongsBySongListId(int list_id) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<ContainEntity> entities = containRepository.findByListId(list_id);
        List<Integer> ids = containRepository.findSongIdsByListId(list_id);
        List<SongEntity> songs = songRepository.findAll(ids);

        for (int index = 0; index < ids.size(); ++index) {
            Map<String, Object> map = new HashMap<>();
            SongEntity songEntity = songs.get(index);
            ContainEntity containEntity = entities.get(index);
            map.put("song_id", songEntity.getSongId());
            map.put("image_id", containEntity.getImageId());
            ImageEntity imageEntity = imageRepository.findOne(containEntity.getImageId());
            map.put("image_url", imageEntity == null ? null : imageEntity.getImageUri());
            map.put("song_name", songEntity.getSongName());
            map.put("artists", songEntity.getAuthorName());
            map.put("duration", songEntity.getLastTime());
            map.put("mp3Url", songEntity.getSongUri());
            list.add(map);
        }
        return list;
    }


    public Map<String, Object> GetSongInfo(int song_id) {
        SongEntity entity = songRepository.findOne(song_id);
        if (entity == null)
            return null;
        Map<String, Object> map = new HashMap<>();
        map.put("netease_id", entity.getNeteaseId());
        if (entity.getNeteaseId() == 0) {
            map.put("artists", entity.getAuthorName());
            map.put("song_name", entity.getSongName());
            map.put("song_url", entity.getSongUri());
        }
        return map;
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

    public List<SongListEntity> GetSongListByUserIdAndPage(int user_id, Pageable pageable) {
        return songListRepository.findSongListByUserIdAndPage(user_id, pageable).getContent();
    }

    public List<CommentEntity> GetCommentByUserIdAndPage(int user_id, Pageable pageable) {
        return commentRepository.findCommentByUserIdAndPage(user_id, pageable).getContent();
    }

    public List<SongListEntity> GetNearestSongList(Pageable pageable) {
        return songListRepository.findNearestSongList(pageable).getContent();
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
        entity.setImageId(2);
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


    public boolean AddSongToList(int list_id, int song_id) {
        System.out.println("list_id :" + list_id + "|song_id :" + song_id);
        if (song_id == 0 || list_id == 0)
            return false;
        ContainEntity entity = new ContainEntity();
        entity.setSongId(song_id);
        entity.setListId(list_id);
        entity.setImageId(3);
        containRepository.save(entity);
        return true;
    }

    @Transactional
    public boolean ChangeSongListInfo(int list_id, String name, String desc, int image_id) {
        int res = songListRepository.updateListInfo(list_id, name, desc, image_id);
        return res == 1;
    }

    @Transactional
    public boolean ChangeSongImage(int list_id, int song_id, int image_id) {
        containRepository.updateSongImage(list_id, song_id, image_id);
        return true;
    }

    public int SongExists(int netease_id) {
        SongEntity entity = songRepository.findByNeteaseId(netease_id);
        return entity == null ? 0 : entity.getSongId();
    }

    public boolean DeleteSong(int list_id, int song_id) {
        ContainEntityPK containEntityPK = new ContainEntityPK();
        containEntityPK.setListId(list_id);
        containEntityPK.setSongId(song_id);
        containRepository.delete(containEntityPK);
        return true;
    }

    @Transactional
    public void updateSong(){
        List<SongEntity> list = songRepository.findAll();
        StringBuilder builder ;
        for (SongEntity entity : list){
            if(entity.getStyles() == null){
                builder = new StringBuilder();
                for (int t = 0; t < 21; ++t) {
                    int i = Math.random() > 0.8 ? 1 : 0;
                    builder.append(i);
                }
                songRepository.updateStyle(entity.getSongId(), builder.toString());
            }
        }
    }

    private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
        return new PageRequest(pageNumber - 1, pagzSize, null);
    }

    private List<Map<String, Object>> fillSongList(int user_id, List<SongListEntity> lists) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SongListEntity s : lists) {
            Map<String, Object> map = new HashMap<>();

            if (user_id != 0) {
                PreferEntityPK entityPK = new PreferEntityPK();
                entityPK.setListId(s.getListId());
                entityPK.setUserId(user_id);
                PreferEntity preferEntity = preferRepository.findOne(entityPK);
                map.put("liked", preferEntity != null);
            }
            map.put("list_id", s.getListId());
            map.put("songlist_name", s.getListName());
            map.put("author_id", s.getUserId());
            map.put("description", s.getProfile());
            map.put("likes", s.getLikes());
            UserEntity userEntity = userRepository.findOne(s.getUserId());
            ImageEntity imageEntity = imageRepository.findOne(s.getImageId());
            map.put("author", userEntity == null ? null : userEntity.getUserName());
            map.put("img_id", s.getImageId());
            map.put("img_url", imageEntity == null ? null : imageEntity.getImageUri());

            list.add(map);
        }
        return list;
    }


}
