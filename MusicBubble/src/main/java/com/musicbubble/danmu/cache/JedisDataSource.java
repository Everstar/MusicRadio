package com.musicbubble.danmu.cache;

import redis.clients.jedis.ShardedJedis;

/**
 * Created by happyfarmer on 5/6/2017.
 */
public interface JedisDataSource {
    public ShardedJedis getRedisClient();

    public void returnResource(ShardedJedis shardedJedis);

    public void returnResource(ShardedJedis shardedJedis, boolean broken);

}
