package com.github.nicklaus4.hbase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * hbase configs
 *
 * @author weishibai
 * @date 2019/03/20 6:40 PM
 */
@ConfigurationProperties(prefix = "hbase")
@Data
public class HBaseConfigs {

    private String zookeeperQuorum;

    private int rpcTimeout;

    private int retries;

    private int operationTimeout;

}
