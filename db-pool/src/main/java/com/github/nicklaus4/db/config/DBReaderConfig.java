package com.github.nicklaus4.db.config;

import lombok.Data;

/**
 * db reader
 *
 * @author weishibai
 * @date 2019/03/19 2:51 PM
 */
@Data
public class DBReaderConfig {

    private String url;

    private String username;

    private String password;

    private String driverClass;

    private long connectionTimeout;

    private long idleTimeout;

    private int maxPoolSize;


}
