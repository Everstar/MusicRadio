package com.musicbubble.tools;

import java.util.Arrays;
import java.util.List;

/**
 * Created by happyfarmer on 2016/12/11.
 */
public class ConstUtil {
    public static final int HOT_LIST_SIZE = 10;
    private static final String[] LANG = new String[]{"zh-cn", "en-us", "ja-jp", "fr-fr", "ko-kr"};
    public static final List LANGUAGES = Arrays.asList(LANG);
    public static final int SONG_STYLE_LENGTH = 21;
    public static final int SONG_LANG_LENGTH = 5;
    public static final String DEFAULT_LANGUAGE = "{\"langs\":[0,0,0,0,0]}";
    public static final String DEFAULT_STYLE = "{\"styles\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]}";
    public static boolean CheckStyle(String style) {
        if(style.length() > 21)
            return false;
        for (char c : style.toCharArray()) {
            if (c != '0' && c != '1')
                return false;
        }
        return true;
    }
}
