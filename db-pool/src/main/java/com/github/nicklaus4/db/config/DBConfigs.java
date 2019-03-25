package com.github.nicklaus4.db.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * db reader configs
 *
 * @author weishibai
 * @date 2019/03/19 2:50 PM
 */
@Data
@ConfigurationProperties(prefix = "ds")
public class DBConfigs {

    private List<DBReaderConfig> readers;

    private DBWriterConfig writer;

}
