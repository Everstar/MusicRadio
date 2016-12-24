package com.musicbubble.service.base;

import com.musicbubble.model.ImageEntity;
import com.musicbubble.model.SongEntity;
import com.musicbubble.repository.ImageRepository;
import com.musicbubble.repository.SongRepository;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
    @Autowired
    private SongRepository songRepository;

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
            System.out.println(res);
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

            System.out.println(lyricStr);

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

    public List<Map<String, Object>> GetPlaylistInfo(String id){
        String uri = new String("http://music.163.com/api/playlist/detail?id=" + id);
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            String res = getOutputFromURL(uri);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(res).path("result");
            JsonNode tracks = rootNode.path("tracks");

            //歌单信息
            Map<String, Object> map = new HashMap<>();
            map.put("name", rootNode.path("name").asText());
            map.put("description", rootNode.path("description").asText());
            list.add(map);

            //歌曲信息
            for(int index = 0; ;index++){
                JsonNode song = tracks.get(index);
                if(song == null)break;

                map = new HashMap<>();
                map.put("name", song.path("name").asText());
                map.put("id", song.path("id").asInt());
                map.put("artists", song.path("artists").get(0).path("name").asText());
                map.put("duration", song.path("duration").asInt());
                map.put("mp3Url", song.path("mp3Url").asText());
                map.put("type", "1");
                list.add(map);
            }
        }catch (MalformedURLException e) {
            System.err.println(uri + "is not a valid url");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //中英文都可以识别
    public List<Map<String, Object>> SearchMusic(String keys, int limit, int type) {
        String uri = new String("http://music.163.com/api/search/get/web?csrf_token=");
        List<Map<String, Object>> list = null;
        try {
            URL url = new URL(uri);
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("Host", "music.163.com");
            uc.setRequestProperty("connection", "Keep-Alive");
            uc.setRequestProperty("Origin", "http://music.163.com");
            uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            uc.setRequestProperty("Referer", "http://music.163.com/search/");

            uc.setDoInput(true);
            uc.setDoOutput(true);

            PrintWriter out = new PrintWriter(new OutputStreamWriter(uc.getOutputStream(), Charset.forName("UTF-8")));
            out.print("hlpretag=<span class=\"s-fc7\">&hlposttag=</span>&s=" + keys + "&type=" + type + "&offset=0&total=true&limit=" + limit);
            out.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("utf-8")));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null)
                builder.append(line);

            System.out.println(builder.toString());
            list = parseMusicList(builder.toString(), limit);

        } catch (MalformedURLException e) {
            System.err.println(uri + "is not a valid url");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public String GetImgUrlById(int id) {

        ImageEntity entity = imageRepository.findOne(id);
        return entity != null ? entity.getImageUri() : null;
    }

    @Transactional
    public int CreateImage(String url, String type) {
        if ((!type.equals("0") && !type.equals("1")) || url == null)
            return 0;
        ImageEntity entity = new ImageEntity();
        entity.setImageType(type);
        entity.setImageUri(url);
        entity = imageRepository.saveAndFlush(entity);
        return entity.getImageId();
    }

    @Transactional
    public int CreateSong(String song_url, String type, String song_name, String song_artists, int netease_id, int duration, String lang,  String styles) {
        if (!type.equals("0") && !type.equals("1"))
            return 0;
        SongEntity entity = new SongEntity();
        entity.setSongName(song_name);
        entity.setNeteaseId(netease_id);
        entity.setAuthorName(song_artists);
        entity.setSongUri(song_url);
        entity.setSongType(type);
        entity.setLastTime(duration);
        entity.setStyles(styles);
        entity.setLanguage(lang);
        entity = songRepository.saveAndFlush(entity);

        return entity.getSongId();
    }


    public int SaveUploadResource(CommonsMultipartFile file, String type, String lang, String styles) {
        int id = 0;
        boolean isImage = type.equals("image");
        try {
            String rootPath = System.getProperty("web.root");
            String relativePath = "resources" + File.separatorChar + (isImage ? "images" : "musics");
            String path = rootPath +  relativePath + File.separatorChar + file.getOriginalFilename();
            File resourceFile = new File(path);
            if (!resourceFile.exists())
                resourceFile.mkdir();

            file.transferTo(resourceFile);

            if (isImage) {
                id = CreateImage(path, "0");
            } else {
                Map<String, String> song_info = parseMp3(path);
                id = CreateSong(path, "0",
                        song_info.get("song_name"),
                        song_info.get("artists"),
                        0,
                        Integer.parseInt(song_info.get("duration")),
                        lang,
                        styles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    //还有许多歌词格式需要测试
    private Map<String, String> parseLyric(String lyric) {
        if (lyric.length() == 0) return null;
        Map<String, String> map = new HashMap<>();
        String[] tokens = lyric.split("\\[|\\]");

        int i = 0;
        while (i < tokens.length - 1) {
            ++i;
            if (tokens[i] == null) continue;
            if (tokens[i].startsWith("00:")) break;
        }
        while (i < tokens.length) {
//            System.out.println(tokens[i] + "&&&" + tokens[i + 1]);
            String[] times = tokens[i].split(":|[.]");
//            System.out.println(times[0] + "!" + times[1] + "!" + times[2]);
            Integer seconds = Integer.parseInt(times[0]) * 60
                    + Integer.parseInt(times[1])
                    + (Integer.parseInt(times[2]) >= 500 ? 1 : 0);
            if (i + 1 < tokens.length)
                map.put(seconds.toString(), tokens[i + 1].trim());
            i += 2;
        }
        return map;
    }

    private List<Map<String, Object>> parseMusicList(String result, int num) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(result).path("result").path("songs");
        for (int i = 0; i < num; ++i) {
            JsonNode songNode = rootNode.get(i);
            if (songNode == null) break;

            Map<String, Object> map = new HashMap<>();
            map.put("song_id", songNode.path("id").asInt());
            map.put("song_name", songNode.path("name").asText());
            map.put("author", songNode.path("artists").get(0).path("name").asText());
            map.put("duration", songNode.path("duration").asInt());
            list.add(map);
        }
        return list;
    }

    private Map<String, String> parseMp3(String path){
        Map<String, String> map = new HashMap<>();
        try {
            MP3File mp3File = new MP3File(path);
            AbstractID3v2Tag tag = mp3File.getID3v2Tag();
            String song_name = tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
            String artists = tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
            MP3AudioHeader audioHeader = mp3File.getMP3AudioHeader();
            Integer duration = (int) (audioHeader.getPreciseTrackLength() * 1000);
            map.put("song_name", song_name);
            map.put("artists", artists);
            map.put("duration", duration.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}

