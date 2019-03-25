package com.github.nicklaus.redis.wrapper;

import java.lang.reflect.Proxy;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nicklaus.redis.HasResource;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * sharded jedis pool facade
 * client consistence hash
 * @author weishibai
 * @date 2019/03/19 7:10 PM
 */
public class ShardedJedisPoolFacade implements HasResource<JedisCommands> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShardedJedisPoolFacade.class);

    private ShardedJedisPool internalPool;


    public ShardedJedisPoolFacade(JedisPoolConfig config, List<JedisShardInfo> shards) {
        this.internalPool = new ShardedJedisPool(config, shards);
    }

    public ShardedJedisPoolFacade(ShardedJedisPool shardedJedisPool) {
        this.internalPool = shardedJedisPool;
    }

    ShardedJedis get0() {
        return internalPool.getResource();
    }

    @Override
    public JedisCommands getResource() {
        try {
            return (JedisCommands) Proxy.newProxyInstance(ShardedJedis.class.getClassLoader()
                    , ShardedJedis.class.getInterfaces(), new PoolableJedisCommands(() -> this));
        } catch (Exception e) {
            LOGGER.error("get sharded jedis error: ", e);
            throw new RuntimeException(e);
        }
    }
}
