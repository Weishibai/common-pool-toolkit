package com.github.nicklaus4.hbase.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.github.nicklaus4.hbase.wrapper.HBaseClientFacade;

/**
 * hbase configuration
 *
 * @author weishibai
 * @date 2019/03/20 5:15 PM
 */
@Configuration
@EnableConfigurationProperties(HBaseConfigs.class)
public class HBaseConfigurator {

    @Autowired
    private Environment env;

    @Autowired
    private HBaseConfigs hBaseConfigs;

    private org.apache.hadoop.conf.Configuration hbaseConfiguration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", hBaseConfigs.getZookeeperQuorum());
        configuration.set("hbase.rpc.timeout", String.valueOf(hBaseConfigs.getRpcTimeout()));
        configuration.set("{hbase.client.retries.number", String.valueOf(hBaseConfigs.getRetries()));
        configuration.set("hbase.operation.timeout", String.valueOf(hBaseConfigs.getOperationTimeout()));
        return configuration;
    }

    @Bean
    public HBaseClientFacade clientFacade() {
        return new HBaseClientFacade(hbaseConfiguration());
    }

}
