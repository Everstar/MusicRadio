package com.musicbubble.tools;

import com.musicbubble.service.base.Languages;
import com.musicbubble.service.base.Styles;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by happyfarmer on 12/22/2016.
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String jsonFromObject(Object object) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, object);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return writer.toString();
    }

    public static Languages langFromJson(String json) {
        Languages languages = new Languages();
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode langs = root.path("langs");
            for (int i = 0; i < Const.SONG_LANG_LENGTH; ++i) {
                languages.setLangs(i, langs.get(i).asInt());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return languages;
    }

    public static Styles styleFromJson(String json) {
        Styles styles = new Styles();
        try {
            JsonNode root = mapper.readTree(json).path("styles");
            for (int i = 0; i < Const.SONG_STYLE_LENGTH; ++i){
                styles.setStyles(i, root.get(i).asInt());
            }
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return styles;
    }

//    public static Object objectFromJson(String json, Class klass) {
//        Object object;
//        try {
//            object = mapper.readValue(json, klass);
//        } catch (RuntimeException e) {
//            throw e;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return object;
//    }
}
