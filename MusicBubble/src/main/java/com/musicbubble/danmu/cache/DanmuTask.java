package com.musicbubble.danmu.cache;

import com.musicbubble.model.DanmuEntity;
import com.musicbubble.repository.DanmuRepository;
import com.musicbubble.repository.SongRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by happyfarmer on 5/6/2017.
 */
@Service
public class DanmuTask extends TimerTask {
    private final static Logger logger = Logger.getLogger(DanmuTask.class);
    @Autowired
    private ShardedJedisPool shardedJedisPool;
    @Autowired
    private DanmuRepository danmuRepository;

    private static AtomicInteger redisStartKey = new AtomicInteger(0);
    private static AtomicInteger redisEndKey = new AtomicInteger(0);

    public void save(String message, String local_time, String global_time, String userID, String songID) {
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        HashMap<String, String> data = new HashMap<>();
        data.put("songID", songID);
        data.put("userID", userID);
        data.put("localTime", local_time);
        data.put("globalTime", global_time);
        data.put("content", message);
        shardedJedis.hmset(String.valueOf(redisEndKey.getAndIncrement()), data);
        shardedJedisPool.returnResource(shardedJedis);
    }

    public List<DanmuEntity> extract() {
        List<DanmuEntity> data = new ArrayList<DanmuEntity>();
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        int endKey = redisEndKey.get();
        //原子性更新
        int startKey = redisStartKey.getAndSet(endKey);
        for (int i = startKey; endKey > 0 && i < endKey; ++i) {
            try {
                Map<String, String> map = shardedJedis.hgetAll(String.valueOf(i));
                DanmuEntity danmuEntity = new DanmuEntity();
                danmuEntity.setUserId(Integer.parseInt(map.get("userID")));
                danmuEntity.setSongId(Integer.parseInt(map.get("songID")));
                danmuEntity.setLocalTime(Integer.parseInt(map.get("localTime")));
                danmuEntity.setGlobalTime(Timestamp.valueOf(map.get("globalTime")));
                danmuEntity.setContent(map.get("content"));
                data.add(danmuEntity);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        shardedJedisPool.returnResource(shardedJedis);
        return data;
    }

    @Override
    public void run() {
        List<DanmuEntity> data = extract();
        if(data.size() > 0){
            danmuRepository.save(data);
            logger.info("danmu save to db : " + data.size());
            logger.info("start :" + redisStartKey.get() + "|end :" + redisEndKey.get());
        }
    }
}
