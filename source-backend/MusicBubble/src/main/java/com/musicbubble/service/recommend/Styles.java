package com.musicbubble.service.recommend;

import com.musicbubble.tools.ConstUtil;

import java.io.Serializable;

/**
 * Created by happyfarmer on 12/22/2016.
 */
public class Styles implements Serializable{
    private int[] styles;

    public Styles() {
        styles = new int[ConstUtil.SONG_STYLE_LENGTH];
    }

    public int[] getStyles(){
        return styles;
    }

    public void setStyles(int i, int value){
        styles[i] = value;
    }

    public void updateStyle(String style) {
        assert style.length() == ConstUtil.SONG_STYLE_LENGTH;

        for (int i = 0; i < ConstUtil.SONG_STYLE_LENGTH; ++i) {
            if (style.charAt(i) == '1')
                styles[i] += 1;
        }
    }

}
