package com.musicbubble.service;

import com.musicbubble.model.SongEntity;
import com.musicbubble.model.TasteEntity;
import com.musicbubble.repository.SongRepository;
import com.musicbubble.repository.TasteRepository;
import com.musicbubble.service.base.Languages;
import com.musicbubble.service.base.MyService;
import com.musicbubble.service.base.Styles;
import com.musicbubble.tools.Const;
import com.musicbubble.tools.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        entity.setLanguage(Const.DEFAULT_LANGUAGE);
        entity.setStyle(Const.DEFAULT_STYLE);
        tasteRepository.save(entity);
        return true;
    }

    private Vector<Integer> parseLanguage(int user_id, String language) {
        Vector<Integer> vector = new Vector<>();
        Languages languages = JsonUtil.langFromJson(language);

        for (int i = 0; i < Const.SONG_LANG_LENGTH; ++i) {
            vector.add(languages.getLangs()[i]);
        }
        return vector;
    }

    private Vector<Integer> parseStyle(int user_id, String style) {
        Vector<Integer> vector = new Vector<>();
        Styles styles = JsonUtil.styleFromJson(style);

        for (int i = 0; i < Const.SONG_STYLE_LENGTH; ++i) {
            vector.add(styles.getStyles()[i]);
        }

        return vector;
    }
}
