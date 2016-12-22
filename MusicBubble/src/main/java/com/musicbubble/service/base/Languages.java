package com.musicbubble.service.base;

import com.musicbubble.tools.Const;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by happyfarmer on 12/22/2016.
 */

public class Languages implements Serializable{
    private int [] langs;

    public Languages() {
        langs = new int[Const.SONG_LANG_LENGTH];
    }

    public int[] getLangs(){
        return langs;
    }

   public void setLangs(int i, int value){
       langs[i] = value;
   }

    public void updateLanguage(String language) {
        assert Const.LANGUAGES.contains(language);

        int index = Const.LANGUAGES.indexOf(language);

        langs[index] += 1;
    }

}
