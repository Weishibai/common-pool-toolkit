package com.nicklaus.redis.wrapper;

import java.io.Closeable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;

/**
 * dynamic proxy which can execute return logic
 *
 * @author weishibai
 * @date 2019/03/19 7:21 PM
 */
final class PoolableJedisCommands<J extends Closeable> implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PoolableJedisCommands.class);

    private final Supplier<Object> poolFactory;

    public PoolableJedisCommands(Supplier<Object> poolFactory) {
        this.poolFactory = poolFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Object pool = poolFactory.get();
        if (null == pool) {
            throw new RuntimeException("no available pool");
        }

        try(J jedis = getJedis(pool)) {
            final Object result = method.invoke(jedis, args);
            LOGGER.debug("invoke jedis method {} args {} result {}", method.getName(), args, result);
            return result;
        } catch (Throwable e) {
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
    }

    private J getJedis(Object pool) {
        if (pool instanceof JedisPoolFacade) {
            JedisPoolFacade facade = (JedisPoolFacade) pool;
            return (J) facade.get0();
        } else if (pool instanceof ShardedJedisPoolFacade) {
            ShardedJedisPoolFacade facade = (ShardedJedisPoolFacade) pool;
            return (J) facade.get0();
        }
        throw new RuntimeException("invalid pool: " + pool);
    }
}
