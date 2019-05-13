package com.github.nicklaus4.memcached.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientStateListener;
import net.rubyeye.xmemcached.MemcachedSessionLocator;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

/**
 * @author weishibai
 * @date 2019/05/12 19:31
 */
public class MemcachedClientBuilder {

    private List<String> addressList;

    private String name;

    private int connectionPoolSize;

    private long connTimeoutMillis;

    private long opTimeoutMillis;

    private int maxQueuedNoReplyOperations;

    private int selectorPoolSize;

    private List<MemcachedClientStateListener> stateListeners;

    private CommandFactory commandFactory;   //default binary

    private int compressionThreshold;

    private MemcachedSessionLocator sessionLocator;

    public static MemcachedClientBuilder newBuilder() {
        return new MemcachedClientBuilder();
    }

    public MemcachedClient build() {
        final XMemcachedClientBuilder builder = new XMemcachedClientBuilder(addressList.stream().reduce((s, s2) -> s + " " + s2).get());
        if (StringUtils.isNotBlank(name)) {
            builder.setName(name);
        }

        if (connectionPoolSize > 0) {
            builder.setConnectionPoolSize(connectionPoolSize);
        }

        if (connTimeoutMillis > 0) {
            builder.setConnectTimeout(connTimeoutMillis);
        }

        if (opTimeoutMillis > 0) {
            builder.setOpTimeout(opTimeoutMillis);
        }

        if (maxQueuedNoReplyOperations > 0) {
            builder.setMaxQueuedNoReplyOperations(maxQueuedNoReplyOperations);
        }

        if (selectorPoolSize > 0) {
            builder.setSelectorPoolSize(selectorPoolSize);
        }

        if (stateListeners != null && !stateListeners.isEmpty()) {
            builder.setStateListeners(stateListeners);
        }

        if (commandFactory != null) {
            builder.setCommandFactory(commandFactory);
        }

        if (compressionThreshold > 0) {
            builder.getTranscoder().setCompressionThreshold(compressionThreshold);
        }

        if (sessionLocator != null) {
            builder.setSessionLocator(sessionLocator);
        }

        try {
            return builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public MemcachedClientBuilder addressList(List<String> addressList) {
        this.addressList = addressList;
        return this;
    }

    public MemcachedClientBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MemcachedClientBuilder connectionPoolSize(int size) {
        this.connectionPoolSize = size;
        return this;
    }

    public MemcachedClientBuilder connTimeoutMillis(long connTimeoutMillis) {
        this.connTimeoutMillis = connTimeoutMillis;
        return this;
    }

    public MemcachedClientBuilder opTimeoutMillis(long opTimeoutMillis) {
        this.opTimeoutMillis = opTimeoutMillis;
        return this;
    }

    public MemcachedClientBuilder maxQueuedNoReplyOperations(int maxQueuedNoReplyOperations) {
        this.maxQueuedNoReplyOperations = maxQueuedNoReplyOperations;
        return this;
    }

    public MemcachedClientBuilder selectorPoolSize(int selectorPoolSize) {
        this.selectorPoolSize = selectorPoolSize;
        return this;
    }

    public MemcachedClientBuilder addStateListener(MemcachedClientStateListener listener) {
        if (this.stateListeners == null) {
            this.stateListeners = new ArrayList<>();
        }
        this.stateListeners.add(listener);
        return this;
    }

    public MemcachedClientBuilder commandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
        return this;
    }

    public MemcachedClientBuilder compressionThreshold(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
        return this;
    }

    public MemcachedClientBuilder sessionLocator(MemcachedSessionLocator sessionLocator) {
        this.sessionLocator = sessionLocator;
        return this;
    }
}
