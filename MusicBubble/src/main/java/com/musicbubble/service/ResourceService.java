package com.musicbubble.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.musicbubble.model.ImageEntity;
import com.musicbubble.repository.ImageRepository;
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
    ImageRepository imageRepository;

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
            JSONObject obj = (JSONObject) JSON.parse(res);
            map = new HashMap<>();
            map.put("song_name", obj.getString("song_name"));
            map.put("song_artists", obj.getString("song_artists"));
            map.put("mp3Url", obj.getString("mp3Url"));

        } catch (MalformedURLException e) {
            System.err.println(uri + "is not a valid url");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return map;
    }


    public Map<String, String> GetImgUrlById(String id) {
        ImageEntity entity = imageRepository.findOne(Integer.parseInt(id));
        Map<String, String> map = null;
        if (entity != null) {
            map.put("imgType", entity.getImageType().equals("0") ? "local" : "outside");
            map.put("imgUrl", entity.getImageUri());

        }
        return map;
    }
}
