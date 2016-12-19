package com.musicbubble.service;

import com.musicbubble.model.ImageEntity;
import com.musicbubble.repository.ImageRepository;
import com.musicbubble.service.base.MyService;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@Service
public class ResourceService extends MyService {

    @Autowired
    private ImageRepository imageRepository;

    private String getOutputFromURL(String uri) throws MalformedURLException, IOException, Exception {
        URL url = new URL(uri);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("UTF-8")));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();

    }

    public Map<String, String> GetMusicInfoById(String id) {
        Map<String, String> map = null;
        String uri = new String("http://music.163.com/api/song/detail/?id=" + id + "&ids=%5B" + id + "%5D");
        try {
            String res = getOutputFromURL(uri);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(res);
            JsonNode songNode = rootNode.path("songs").get(0);
            map = new HashMap<>();
            map.put("song_name", songNode.path("name").asText());
            map.put("song_artists", songNode.path("artists").get(0).path("name").asText());
            map.put("mp3Url", songNode.path("mp3Url").asText());

        } catch (MalformedURLException e) {
            System.err.println(uri + "is not a valid url");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public Map<String, String> GetMusicLyric(String id) {
        String uri = new String("http://music.163.com/api/song/media?id=" + id);
        Map<String, String> lyric = null;
        try {
            String res = getOutputFromURL(uri);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(res);
            String lyricStr = rootNode.path("lyric").asText();
            lyric = parseLyric(lyricStr);
        } catch (MalformedURLException e) {
            System.err.println(uri + "is not a valid url");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lyric;
    }

    public List<Map<String, Object>> SearchMusic(String keys, int limit, int type){
        return null;

    }

    public String GetImgUrlById(int id) {
        ImageEntity entity = imageRepository.findOne(id);
        return entity != null ? entity.getImageUri() : null;
    }

    private Map<String, String> parseLyric(String lyric) {
        Map<String, String> map = new HashMap<>();
        lyric = lyric.trim();
        String[] tokens = lyric.split("\\[|\\]");
        for (int i = 1; i < tokens.length; ) {
//            System.out.println(tokens[i] + "&&&" + tokens[i + 1]);
            String[] times = tokens[i].split(":|[.]");
//            System.out.println(times[0] + "!" + times[1] + "!" + times[2]);
            Integer seconds = Integer.parseInt(times[0]) * 60
                    + Integer.parseInt(times[1])
                    + (Integer.parseInt(times[2]) >= 500 ? 1 : 0);
            map.put(seconds.toString(), tokens[i + 1].trim());
            i += 2;
        }
        return map;
    }
}
