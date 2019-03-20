package com.nicklaus.hbase.wrapper;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.DisposableBean;

import com.google.common.base.Throwables;

/**
 * hbase client facade
 *
 * @author weishibai
 * @date 2019/03/20 5:01 PM
 */
public class HBaseClientFacade implements DisposableBean {

    private final Configuration configuration;

    private final Connection connection;

    public HBaseClientFacade(Configuration configuration) {
        this.configuration = configuration;
        try {
            this.connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException("create hbase connection error: ", e);
        }
    }

    public Connection connection() {
        return connection;
    }

    public Configuration configuration() {
        return configuration;
    }

    @Override
    public void destroy() throws Exception {
        connection.close();
    }
}
