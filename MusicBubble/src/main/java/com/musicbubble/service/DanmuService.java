package com.musicbubble.service;

import com.musicbubble.model.DanmuEntity;
import com.musicbubble.repository.DanmuRepository;
import com.musicbubble.service.base.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 5/6/2017.
 */
@Service
public class DanmuService extends MyService {
    @Autowired
    private DanmuRepository danmuRepository;

    public List<Map<String, Object>> GetDanmuBySongId(int song_id){
        List<Map<String, Object>> danmus = new ArrayList<>();
        for (DanmuEntity entity : danmuRepository.findDanmuBySongId(song_id)){
            Map<String, Object> danmu = new HashMap<>();
            danmu.put("user_id", entity.getUserId());
            danmu.put("local_time", entity.getLocalTime());
            danmu.put("global_time", entity.getGlobalTime());
            danmu.put("content", entity.getContent());
            danmus.add(danmu);
        }
        return danmus;
    }
}
