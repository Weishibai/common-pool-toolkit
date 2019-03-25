package com.github.nicklaus4.db.datasource;

import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.lang.Nullable;

/**
 * reader datasource
 *
 * @author weishibai
 * @date 2019/03/19 5:43 PM
 */
public class ReaderDataSource extends DelegatingDataSource {

    private final Supplier<DataSource> readerFactory;

    public ReaderDataSource(Supplier<DataSource> readerFactory) {
        this.readerFactory = readerFactory;
    }

    @Nullable
    @Override
    public DataSource getTargetDataSource() {
        return readerFactory.get();
    }
}
