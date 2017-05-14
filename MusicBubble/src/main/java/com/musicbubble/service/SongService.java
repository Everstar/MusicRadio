package com.musicbubble.service;

import com.musicbubble.service.base.MyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Timer;

/**
 * Created by happyfarmer on 2017/5/11.
 */
@Service
public class SongService extends MyService {
    private static Logger logger = Logger.getLogger(SongService.class);

    @Autowired
    private SongTask songTask;

    private Timer songTimer;

    public void incPlayedTimes(int song_id) {
        if (songTimer == null) {
            songTimer = new Timer();
            songTimer.schedule(songTask, 3000, 60000);
            logger.info("songTimer start!!!");
        }
        songTask.incPlayTimes(song_id);
    }
}
