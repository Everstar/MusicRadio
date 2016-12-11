package com.musicbubble.service;

import com.musicbubble.model.ImageEntity;
import com.musicbubble.repository.ImageRepository;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.util.HashMap;
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
        InputStream buffer = new BufferedInputStream(uc.getInputStream());
        Reader reader = new InputStreamReader(buffer);
        int c;
        StringBuilder result = new StringBuilder();
        while ((c = reader.read()) != -1) {
            result.append((char) c);
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

    public String GetMusicLyric(String id){
        String uri = new String("http://music.163.com/api/song/media?id=" + id);
        String lyric = null;
        try {
            String res = getOutputFromURL(uri);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(res);
            lyric = rootNode.path("lyric").asText();
            System.out.println(lyric);
        }catch (MalformedURLException e) {
            System.err.println(uri + "is not a valid url");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lyric;
    }



    public String GetImgUrlById(int id) {
        ImageEntity entity = imageRepository.findOne(id);
        return entity!=null?entity.getImageUri():null;
    }
}
