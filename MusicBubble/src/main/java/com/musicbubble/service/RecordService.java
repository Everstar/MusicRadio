package com.musicbubble.service;

import com.musicbubble.model.SongEntity;
import com.musicbubble.model.TasteEntity;
import com.musicbubble.repository.SongRepository;
import com.musicbubble.repository.TasteRepository;
import com.musicbubble.service.recommend.Languages;
import com.musicbubble.service.base.MyService;
import com.musicbubble.service.recommend.Styles;
import com.musicbubble.tools.ConstUtil;
import com.musicbubble.tools.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by happyfarmer on 12/22/2016.
 */
@Service
public class RecordService extends MyService {

    @Autowired
    private TasteRepository tasteRepository;
    @Autowired
    private SongRepository songRepository;

    @Transactional
    public boolean recordPlayMusic(int user_id, int song_id) {
        SongEntity entity = songRepository.findOne(song_id);
        if (entity == null) return false;

        String song_language = entity.getLanguage();
        String song_style = entity.getStyles();

        TasteEntity tasteEntity = tasteRepository.findOne(user_id);
        Languages languages = JsonUtil.langFromJson(tasteEntity.getLanguage());
        languages.updateLanguage(song_language);
        Styles styles = JsonUtil.styleFromJson(tasteEntity.getStyle());
        styles.updateStyle(song_style);

        String lang = JsonUtil.jsonFromObject(languages);
        String style = JsonUtil.jsonFromObject(styles);

        tasteRepository.update(user_id, lang, style);

        return true;
    }

    @Transactional
    public boolean CreateTaste(int user_id) {
        TasteEntity entity = new TasteEntity();
        entity.setUserId(user_id);
        entity.setLanguage(ConstUtil.DEFAULT_LANGUAGE);
        entity.setStyle(ConstUtil.DEFAULT_STYLE);
        tasteRepository.save(entity);
        return true;
    }

    public List<Integer> getOneUserStyle(int user_id){
        TasteEntity t=tasteRepository.findOne(user_id);
        String style=t.getStyle();

        return parseStyle(style);
    }

    public List<List<Integer>> getRangeUserStyle(int pre_id,int las_id){
        List<String> style=tasteRepository.findRangeOfUser(pre_id,las_id);
        List<List<Integer>> returnStyle=new ArrayList<>();
        for(String userStyle:style){
            returnStyle.add(parseStyle(userStyle));
        }

        return returnStyle;
    }

    private List<Integer> parseLanguage(int user_id, String language) {
        List<Integer> list = new ArrayList<>();
        Languages languages = JsonUtil.langFromJson(language);

        for (int i = 0; i < Const.SONG_LANG_LENGTH; ++i) {
            list.add(languages.getLangs()[i]);
        }
        return list;
    }

    private List<Integer> parseStyle(String style) {
        List<Integer> list = new ArrayList<>();
        Styles styles = JsonUtil.styleFromJson(style);


        for (int i = 0; i < Const.SONG_STYLE_LENGTH; ++i) {
            list.add(styles.getStyles()[i]);
        }

        return list;
    }
}
