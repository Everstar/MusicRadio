package com.musicbubble.controller;

import com.musicbubble.service.base.AccountService;
import com.musicbubble.service.SongListService;
import com.musicbubble.service.base.ResourceService;
import com.musicbubble.tools.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@RestController
public class SongListController implements Serializable {
    @Autowired
    private SongListService songListService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/api/import", method = RequestMethod.GET)
    public ResponseEntity<Object> importSongs(@CookieValue("token") String token, @RequestParam("id") String songlist_id) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = resourceService.GetPlaylistInfo(songlist_id);
        Map<String, Object> map = list.get(0);
        int list_id = songListService.CreateSongList(user_id, (String) map.get("name"), (String) map.get("description"));
        for (int index = 1; index < list.size(); ++index) {
            map = list.get(index);
            int song_id = resourceService.CreateSong(
                    (String) map.get("mp3Url"),
                    (String) map.get("type"),
                    (String) map.get("name"),
                    (String) map.get("artists"),
                    (Integer) map.get("id"),
                    (Integer) map.get("duration"),
                    null,
                    null
            );
            songListService.AddSongToList(list_id, song_id);
        }
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/hotlist", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> getHotList(@RequestParam(value = "num", defaultValue = "10") int num) {
        List<Map<String, Object>> list = songListService.GetHotList(num);
        HttpStatus status = HttpStatus.OK;
        if (list.isEmpty()) status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(list, status);
    }

    //tested
    @RequestMapping(value = "/songlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> getOwnSongLists(@CookieValue("token") String token) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = songListService.GetSongListByUserId(user_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //tested
    @RequestMapping(value = "/songlist", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> getFollowSongLists(@CookieValue("token") String token, @RequestParam("id") int follow_id) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = songListService.GetSongListByUserId(follow_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/songlist/one", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> getSongsOfSongList(@CookieValue("token") String token, @RequestParam("id") int list_id) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        List<Map<String, Object>> list = songListService.GetSongsBySongListId(list_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //tested
    @RequestMapping(value = "/newlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> addSongList(@CookieValue("token") String token, @RequestBody Map<String, String> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        String song_name = data.get("songlist_name").trim();
        String desc = data.get("description").trim();
        int list_id = songListService.CreateSongList(user_id, song_name, desc);
        Map<String, Object> map = new HashMap<>();
        map.put("songlist_id", list_id);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //tested
    @RequestMapping(value = "/deletelist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> removeSongList(@CookieValue("token") String token, @RequestBody Map<String, Integer> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        int list_id = data.get("id");
        boolean res = songListService.DeleteSongList(list_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<>(map, status);
    }


    //tested
    @RequestMapping(value = "/changelist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> changeSongList(@CookieValue("token") String token
            , @RequestParam("image_file") CommonsMultipartFile image, HttpServletRequest request) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        Integer list_id = Integer.parseInt(request.getParameter("songlist_id"));
        String song_name = request.getParameter("songlist_name").trim();
        String desc = request.getParameter("description").trim();
        String image_url = request.getParameter("image_url").trim();
        Integer image_id = Integer.parseInt(request.getParameter("image_id"));//原来的id

        if (image != null
                && image.getSize() > 0
                && image.getContentType().startsWith("image")) {
            //上传图片
            image_id = resourceService.SaveUploadResource(image, "image", null, null);

        } else if (image_url != null) {
            //外链图片
            image_id = resourceService.CreateImage(image_url, "1");
        } else {
            //不改变背景图
        }

        boolean res = songListService.ChangeSongListInfo(list_id, song_name, desc, image_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<>(map, status);
    }


    @RequestMapping(value = "/addsong/netease", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> addSongByNetEase(@CookieValue("token") String token, @RequestBody Map<String, Object> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        Integer list_id = (Integer) data.get("songlist_id");
        Integer netease_id = (Integer) data.get("netease_id");
        String song_name = (String)data.get("song_name");
        String song_artists = (String)data.get("song_artists");
        String song_url = (String)data.get("mp3Url");
        Integer duration = (Integer) data.get("duration");
        String styles = (String)data.get("styles");
        String language = (String)data.get("language");

        boolean res;
        int song_id = 0;
        if ((song_id = songListService.SongExists(netease_id)) != 0) {
            res = false;
        } else {
            song_id = resourceService.CreateSong(song_url, "1", song_name, song_artists, netease_id, duration, language, styles);
            res = songListService.AddSongToList(list_id, song_id);
        }
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("song_id", song_id);
        return new ResponseEntity<>(map, status);
    }

    //tested
    @RequestMapping(value = "/addsong/songlist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> addSongBySongList(@CookieValue("token") String token, @RequestBody Map<String, Integer> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        Integer list_id = data.get("songlist_id");
        Integer song_id = data.get("song_id");

        boolean res = songListService.AddSongToList(list_id, song_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<>(map, status);
    }

    //tested
    @RequestMapping(value = "/addsong/upload", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> addSongByUpload(@CookieValue("token") String token,
        @RequestParam("song_file") CommonsMultipartFile music, HttpServletRequest request) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        Integer list_id = Integer.parseInt(request.getParameter("songlist_id"));
        String language = request.getParameter("language");
        String styles = request.getParameter("styles");

        int song_id = 0;
        if (music != null
                && music.getSize() > 0
                && music.getContentType().startsWith("audio")) {
            //上传图片
            song_id = resourceService.SaveUploadResource(music, "music", language, styles);
        }

        boolean res = songListService.AddSongToList(list_id, song_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("song_id", song_id);
        return new ResponseEntity<>(map, status);
    }


    //tested
    @RequestMapping(value = "/removesong", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> removeSong(@CookieValue("token") String token, @RequestBody Map<String, Integer> data) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        int list_id = data.get("songlist_id");
        int song_id = data.get("song_id");

        boolean res = songListService.DeleteSong(list_id, song_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<>(map, status);
    }

    //tested
    @RequestMapping(value = "/changesong", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Object> changeSong(@CookieValue("token") String token
            , @RequestParam("image_file") CommonsMultipartFile image, HttpServletRequest request) {
        int user_id = accountService.IdentifyUser(token);
        if (user_id == -1) return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);

        Integer list_id = Integer.parseInt(request.getParameter("songlist_id"));
        Integer song_id = Integer.parseInt(request.getParameter("song_id"));
        String image_url = request.getParameter("image_url").trim();
        Integer image_id = Integer.parseInt(request.getParameter("image_id"));//原来的id

        if (image != null
                && image.getSize() > 0
                && image.getContentType().startsWith("image")) {
            //上传图片
            image_id = resourceService.SaveUploadResource(image, "image", null, null);

        } else if (image_url != null && !image_url.equals("")) {
            //外链图片
            image_id = resourceService.CreateImage(image_url, "1");
        } else {
            //不改变背景图
        }

        boolean res = songListService.ChangeSongImage(list_id, song_id, image_id);
        HttpStatus status = res ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
        Map<String, Object> map = new HashMap<>();
        map.put("result", res);
        return new ResponseEntity<>(map, status);
    }


}
