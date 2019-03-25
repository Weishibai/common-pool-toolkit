package com.github.nicklaus4.redis.config;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.github.nicklaus4.redis.wrapper.JedisPoolFacade;

import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis pool configuration
 *
 * @author weishibai
 * @date 2019/03/19 6:43 PM
 */
@Configuration
@EnableConfigurationProperties(JedisConfigs.class)
public class JedisPoolConfiguration {

    private static final Integer POOL_TOTAL_MAX_COUNT = 100;

    private final JedisConfigs jedisConfigs;

    public JedisPoolConfiguration(JedisConfigs jedisConfigs) {
        this.jedisConfigs = jedisConfigs;
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(POOL_TOTAL_MAX_COUNT);
        poolConfig.setMaxIdle(POOL_TOTAL_MAX_COUNT);
        poolConfig.setMinIdle(0);
        poolConfig.setMaxWaitMillis(SECONDS.toMillis(2));
        poolConfig.setTestOnBorrow(true);
        poolConfig.setMinEvictableIdleTimeMillis(MINUTES.toMillis(10)); //10分钟还没用就关闭掉
        poolConfig.setTimeBetweenEvictionRunsMillis(MINUTES.toMillis(1)); //每1分钟检查一次闲置连接，进行淘汰
        poolConfig.setJmxEnabled(false);
        return poolConfig;
    }

    @Bean
    @Conditional(JedisPoolConditional.class)
    public JedisPoolFacade jedisPoolFacade() {
        final JedisConfigs.ShardInfo normal = jedisConfigs.getNormal();
        return new JedisPoolFacade(jedisPoolConfig(), normal.getHost(), normal.getPort()
                , normal.getConnectTimeout(), normal.getOperationTimeout());
    }

//    @Bean
//    @Conditional(JedisShardedPoolConditional.class)
//    public void jedisShardedPoolFacade() {
//        return new ShardedJedisPoolFacade();
//    }
}
