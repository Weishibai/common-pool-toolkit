package com.nicklaus.db.config;

import lombok.Data;

/**
 * db writer config
 *
 * @author weishibai
 * @date 2019/03/19 3:19 PM
 */
@Data
public class DBWriterConfig {

    private String url;

    private String username;

    private String password;

    private String driverClass;

    private long maxLifeTime;

    private long connectionTimeout;

    private long idleTimeout;

    private int maxPoolSize;
}
