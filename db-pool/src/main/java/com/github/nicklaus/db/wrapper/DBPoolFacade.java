package com.github.nicklaus.db.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

/**
 * db pool facade
 *
 * @author weishibai
 * @date 2019/03/19 3:42 PM
 */
public class DBPoolFacade implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBPoolFacade.class);

    private final NamedParameterJdbcTemplate writer;

    private final NamedParameterJdbcTemplate reader;

    public DBPoolFacade(NamedParameterJdbcTemplate writer,  NamedParameterJdbcTemplate reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public NamedParameterJdbcTemplate writer() {
        return writer;
    }

    public NamedParameterJdbcTemplate reader() {
        return reader;
    }

    @Override
    public void destroy() throws Exception {
        try {
            ((HikariDataSource) writer.getJdbcTemplate().getDataSource()).close();
        } catch (Exception e) {
            LOGGER.warn("close writer db resource error: ", e);
        }

        try {
            if (writer() != reader()) {
                ((HikariDataSource) reader.getJdbcTemplate().getDataSource()).close();
            }
        } catch (Exception e) {
            LOGGER.warn("close reader db resource error: ", e);
        }
    }
}
