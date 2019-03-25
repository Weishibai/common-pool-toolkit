package com.github.nicklaus.redis.wrapper;

import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nicklaus.redis.HasResource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis pool facade
 *
 * @author weishibai
 * @date 2019/03/19 6:38 PM
 */
public class JedisPoolFacade implements HasResource<JedisCommands> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JedisPoolFacade.class);

    private JedisPool internalPool;

    public JedisPoolFacade(JedisPool internalPool) {
        this.internalPool = internalPool;
    }

    public JedisPoolFacade(JedisPoolConfig poolConfig, String host, int port) {
        this.internalPool = new JedisPool(poolConfig, host, port);
    }

    public JedisPoolFacade(JedisPoolConfig poolConfig, String host, int port, int connTimeOut, int operationTimeOut) {
        this.internalPool = new JedisPool(poolConfig, host, port, connTimeOut, operationTimeOut, null, 0, null,
                false, null, null, null);
    }

    public JedisPoolFacade(String host, int port) {
        this.internalPool = new JedisPool(host, port);
    }

    @Override
    public JedisCommands getResource() {
        try {
            return (JedisCommands) Proxy.newProxyInstance(Jedis.class.getClassLoader()
                    , Jedis.class.getInterfaces(), new PoolableJedisCommands(() -> this));
        } catch (Exception e) {
            LOGGER.error("get jedis error: ", e);
            throw new RuntimeException(e);
        }
    }

    Jedis get0() {
        return internalPool.getResource();
    }

}
