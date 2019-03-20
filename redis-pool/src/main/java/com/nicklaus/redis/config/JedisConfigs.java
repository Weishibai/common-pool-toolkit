package com.nicklaus.redis.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * jedis configs
 *
 * @author weishibai
 * @date 2019/03/20 11:05 AM
 */
@Data
@ConfigurationProperties(prefix = "jedis")
public class JedisConfigs {

    private ShardInfo normal;

    private List<ShardInfo> shards;

    @Data
    static final class ShardInfo {
        private String host;

        private int port;

        private int connectTimeout;

        private int operationTimeout;
    }

}
