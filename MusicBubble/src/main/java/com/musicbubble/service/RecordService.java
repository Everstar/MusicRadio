package com.musicbubble.service;

import com.musicbubble.model.SongEntity;
import com.musicbubble.model.TastelanguageEntity;
import com.musicbubble.model.TastestyleEntity;
import com.musicbubble.repository.LanguageRepository;
import com.musicbubble.repository.SongRepository;
import com.musicbubble.repository.StyleRepository;
import com.musicbubble.service.base.MyService;
import com.musicbubble.tools.Const;
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
    private StyleRepository styleRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private SongRepository songRepository;

    @Transactional
    public boolean recordPlayMusic(int user_id, int song_id) {
        SongEntity entity = songRepository.findOne(song_id);
        if (entity == null) return false;

        String language = entity.getLanguage();
        String style = entity.getStyles();

        int index = Const.LANGUAGES.indexOf(language);
        Integer[] langs = new Integer[Const.LANGUAGES.size()];
        Integer[] styles = new Integer[Const.SONG_STYLE_LENGTH];
        if (index >= 0 && index <= langs.length - 1) {
            langs[index] += 1;
        }

        for (int i = 0; i < Const.SONG_STYLE_LENGTH; ++i) {
            if (style.charAt(i) == '1') {
                styles[i] += 1;
            }
        }

        languageRepository.update(langs[0], langs[1], langs[2], langs[3], langs[4], user_id);
//        styleRepository.update(styles);
        return true;
    }

    private Vector<Integer> parseLanguage(int user_id, String language) {
        Vector<Integer> vector = new Vector<>();
        TastelanguageEntity entity = languageRepository.findOne(user_id);
        vector.add(entity.getLang1());
        vector.add(entity.getLang2());
        vector.add(entity.getLang3());
        vector.add(entity.getLang4());
        return vector;
    }

    private Vector<Integer> parseStyle(int user_id, String style) {
        Vector<Integer> vector = new Vector<>();
        TastestyleEntity entity = styleRepository.findOne(user_id);
        vector.add(entity.getTaste1());
        vector.add(entity.getTaste2());
        vector.add(entity.getTaste3());
        vector.add(entity.getTaste4());
        vector.add(entity.getTaste5());
        vector.add(entity.getTaste6());
        vector.add(entity.getTaste7());
        vector.add(entity.getTaste8());
        vector.add(entity.getTaste9());
        vector.add(entity.getTaste10());
        vector.add(entity.getTaste11());
        vector.add(entity.getTaste12());
        vector.add(entity.getTaste13());
        vector.add(entity.getTaste14());
        vector.add(entity.getTaste15());
        vector.add(entity.getTaste16());
        vector.add(entity.getTaste17());
        vector.add(entity.getTaste18());
        vector.add(entity.getTaste19());
        vector.add(entity.getTaste20());
        vector.add(entity.getTaste21());
        return vector;
    }
}
