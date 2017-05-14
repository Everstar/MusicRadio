package com.musicbubble.service;

import com.musicbubble.danmu.cache.DanmuTask;
import com.musicbubble.model.SongEntity;
import com.musicbubble.repository.SongRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by happyfarmer on 2017/5/11.
 */
@Service
public class SongTask extends TimerTask{
    private final static Logger logger = Logger.getLogger(SongTask.class);
    @Autowired
    private ShardedJedisPool shardedJedisPool;
    @Autowired
    private SongRepository songRepository;


    public Map<String, String> extract(){
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        Map<String, String> raw = shardedJedis.hgetAll("playedTimes");
        shardedJedisPool.returnResource(shardedJedis);
        return raw;
    }

    public void incPlayTimes(int song_id){
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        String s_id = String.valueOf(song_id);
        if (!shardedJedis.exists("playedTimes")){
            HashMap<String, String> data = new HashMap<>();
            data.put("0", "0");
            shardedJedis.hmset("playedTimes", data);
        }


        if(shardedJedis.hexists("playedTimes", s_id)){
            int times = Integer.parseInt(shardedJedis.hget("playedTimes", s_id));
            shardedJedis.hset("playedTimes", s_id, String.valueOf(times + 1));
        }else {
            SongEntity entity = songRepository.findOne(song_id);
            if (entity != null){
                shardedJedis.hset("playedTimes", s_id, String.valueOf(entity.getPlayedTimes() + 1));
            }
        }

        logger.info("[" + song_id + "] : " + shardedJedis.hget("playedTimes", s_id));
        shardedJedisPool.returnResource(shardedJedis);
    }

    @Override
    public void run() {
        Map<String, String> data = extract();
        if (data.size() == 0)
            return;
        for(Map.Entry<String, String> entry : data.entrySet()){
            songRepository.updatePlayedTimes(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()));
        }

        logger.info("update songs!!!");
    }
}
