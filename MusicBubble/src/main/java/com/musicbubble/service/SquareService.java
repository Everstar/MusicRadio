package com.musicbubble.service;

import com.musicbubble.model.CommentEntity;
import com.musicbubble.model.PreferEntity;
import com.musicbubble.model.SongListEntity;
import com.musicbubble.repository.CommentRepository;
import com.musicbubble.repository.PreferRepository;
import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.base.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 12/21/2016.
 */
@Service
public class SquareService extends MyService{
    @Autowired
    private AccountService accountService;
    @Autowired
    private PreferRepository preferRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SongListService songListService;

    public List<Map<String, Object>> GetSquare(){
        List<Map<String, Object>> square = new ArrayList<>();
        final int page_size = 30;
        List<Map<String, Object>> list = GetNearestPrefer(page_size);
        square.addAll(list);
        list = GetNearestComment(page_size);
        square.addAll(list);
        list = GetNearestCreate(page_size);
        square.addAll(list);

        return square;
    }

    public List<Map<String, Object>> GetNearestPrefer(int page_size){
        List<Map<String, Object>> near = new ArrayList<>();
        PageRequest request = buildPageRequest(1, page_size);
        List<PreferEntity> prefers = preferRepository.findNearestPrefer(request).getContent();
        for (PreferEntity p : prefers){
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getUserId());
            map.put("username", accountService.GetUserNameById(p.getUserId()));
            map.put("type", "like");
            map.put("songlist_id", p.getListId());
            map.put("songlist_name", songListService.GetSongListNameById(p.getListId()));
            near.add(map);
        }
        return near;
    }
    public List<Map<String, Object>> GetNearestComment(int page_size){
        List<Map<String, Object>> near = new ArrayList<>();
        PageRequest request = buildPageRequest(1, page_size);
        List<CommentEntity> comments = commentRepository.findNearestComment(request).getContent();
        for (CommentEntity c : comments){
            Map<String, Object> map = new HashMap<>();
            map.put("id", c.getUserId());
            map.put("username", accountService.GetUserNameById(c.getUserId()));
            map.put("type", "comment");
            map.put("songlist_id", c.getListId());
            map.put("songlist_name", songListService.GetSongListNameById(c.getListId()));
            near.add(map);
        }
        return near;

    }
    public List<Map<String, Object>> GetNearestCreate(int page_size){
        List<Map<String, Object>> near = new ArrayList<>();
        PageRequest request = buildPageRequest(1, page_size);
        List<SongListEntity> songs = songListService.GetNearestSongList(request);
        for(SongListEntity s : songs){
            Map<String, Object> map = new HashMap<>();
            map.put("id", s.getUserId());
            map.put("username", accountService.GetUserNameById(s.getUserId()));
            map.put("type", "create");
            map.put("songlist_id", s.getListId());
            map.put("songlist_name", s.getListName());
            near.add(map);
        }
        return near;
    }

    private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
        return new PageRequest(pageNumber - 1, pagzSize, null);
    }
}
