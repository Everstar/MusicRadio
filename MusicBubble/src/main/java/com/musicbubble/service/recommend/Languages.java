package com.musicbubble.service.recommend;

import com.musicbubble.tools.ConstUtil;

import java.io.Serializable;

/**
 * Created by happyfarmer on 12/22/2016.
 */

public class Languages implements Serializable{
    private int [] langs;

    public Languages() {
        langs = new int[ConstUtil.SONG_LANG_LENGTH];
    }

    public int[] getLangs(){
        return langs;
    }

   public void setLangs(int i, int value){
       langs[i] = value;
   }

    public void updateLanguage(String language) {
        assert ConstUtil.LANGUAGES.contains(language);

        int index = ConstUtil.LANGUAGES.indexOf(language);

        langs[index] += 1;
    }

}
