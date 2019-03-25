package com.github.nicklaus.db.config;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.github.nicklaus.db.datasource.ReaderDataSource;
import com.github.nicklaus.db.wrapper.DBPoolFacade;
import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * db pool configuration
 *
 * @author weishibai
 * @date 2019/03/19 2:10 PM
 */
@Configuration
@EnableConfigurationProperties(DBConfigs.class)
public class DBPoolConfiguration {

    private final DBConfigs dbConfigs;

    public DBPoolConfiguration(DBConfigs dbConfigs) {
        this.dbConfigs = dbConfigs;
    }

    @Bean
    @ConditionalOnProperty(name = "ds-writer-enabled", havingValue = "true")
    public DataSource writerDataSource() {
        HikariConfig config = new HikariConfig();
        final DBWriterConfig writer = dbConfigs.getWriter();
        config.setJdbcUrl(writer.getUrl());
        config.setUsername(writer.getUsername());
        config.setPassword(writer.getPassword());
        config.setDriverClassName(writer.getDriverClass());
        config.setConnectionTimeout(writer.getConnectionTimeout());
        config.setIdleTimeout(writer.getIdleTimeout() > 0 ? writer.getIdleTimeout() : 500000);
        config.setMaxLifetime(writer.getMaxLifeTime() > 0 ? writer.getMaxLifeTime() : 1800000);
        config.setMaximumPoolSize(writer.getMaxPoolSize() > 0 ? writer.getMaxPoolSize() : 500);

        config.addDataSourceProperty("cachePrepStmts", "true"); //是否自定义配置，为true时下面两个参数才生效
        config.addDataSourceProperty("prepStmtCacheSize", "250"); //连接池大小默认25，官方推荐250-500
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); //单条语句最大长度默认256，官方推荐2048
        config.addDataSourceProperty("useServerPrepStmts", "true"); //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("useLocalTransactionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");

        return new HikariDataSource(config);
    }

    @Bean
    @ConditionalOnProperty(name = "ds-reader-enabled", havingValue = "true")
    public ReaderDataSource readerDataSource() {
        List<DataSource> readers = Lists.newArrayList();
        final List<DBReaderConfig> configs = dbConfigs.getReaders();
        if (null == configs) {
            return null;
        }

        configs.forEach(rc -> {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(rc.getUrl());
            config.setUsername(rc.getUsername());
            config.setPassword(rc.getPassword());
            config.setDriverClassName(rc.getDriverClass());
            config.setConnectionTimeout(rc.getConnectionTimeout());
            config.setIdleTimeout(rc.getIdleTimeout() > 0 ? rc.getIdleTimeout() : 500000);
            config.setMaximumPoolSize(rc.getMaxPoolSize() > 0 ? rc.getMaxPoolSize() : 500);
            config.setReadOnly(true);

            config.addDataSourceProperty("cachePrepStmts", "true"); //是否自定义配置，为true时下面两个参数才生效
            config.addDataSourceProperty("prepStmtCacheSize", "250"); //连接池大小默认25，官方推荐250-500
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); //单条语句最大长度默认256，官方推荐2048
            config.addDataSourceProperty("useServerPrepStmts", "true"); //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
            config.addDataSourceProperty("useLocalSessionState", "true");
            config.addDataSourceProperty("useLocalTransactionState", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("cacheResultSetMetadata", "true");
            config.addDataSourceProperty("cacheServerConfiguration", "true");
            config.addDataSourceProperty("elideSetAutoCommits", "true");
            config.addDataSourceProperty("maintainTimeStats", "false");
            readers.add(new HikariDataSource(config));
        });

        return new ReaderDataSource(() -> {
            final int current = ThreadLocalRandom.current().nextInt(readers.size());
            return readers.get(current);
        });
    }

    private NamedParameterJdbcTemplate writerJdbcTemplate() {
        return new NamedParameterJdbcTemplate(writerDataSource());
    }

    private NamedParameterJdbcTemplate readerJdbcTemplate() {
        return new NamedParameterJdbcTemplate(readerDataSource());
    }

    @Bean
    @ConditionalOnProperty(name = "ds-reader-enabled", havingValue = "true")
    public DBPoolFacade dbPoolFacade() {
        return new DBPoolFacade(writerJdbcTemplate(), readerJdbcTemplate());
    }

    @Bean
    @ConditionalOnProperty(name = "ds-reader-enabled", havingValue = "false")
    public DBPoolFacade dbPoolFacadeSingle() {
        final NamedParameterJdbcTemplate writer = writerJdbcTemplate();
        return new DBPoolFacade(writer, writer);
    }

}
