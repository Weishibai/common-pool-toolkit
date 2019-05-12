package com.github.nicklaus4.localcache.config;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.github.nicklaus4.localcache.model.ReloadMode;

/**
 * @author weishibai
 * @date 2019/05/12 12:43
 */
public class CaffeineCacheBuilder<K, V> {

    private long expireAfterWriteMillis;

    private long expireAfterAccessMillis;

    private long refreshAfterWriteMillis;

    private int initCapacity;

    private int maximumSize;

    private boolean weakKey;

    private Executor executor;

    private ReloadMode reloadMode;

    private CacheLoader<? super K, V> cacheLoader;

    private Supplier<StatsCounter> statsCounterSupplier;

    private RemovalListener<? super K, ? super V> removalListener;

    public static <K, V> CaffeineCacheBuilder<K, V> newBuilder() {
        return new CaffeineCacheBuilder<>();
    }

    public long getExpireAfterWriteMillis() {
        return expireAfterWriteMillis;
    }

    public long getExpireAfterAccessMillis() {
        return expireAfterAccessMillis;
    }

    public long getRefreshAfterWriteMillis() {
        return refreshAfterWriteMillis;
    }

    public int getInitCapacity() {
        return initCapacity;
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public boolean isWeakKey() {
        return weakKey;
    }

    public Executor getExecutor() {
        return executor;
    }

    public ReloadMode getReloadMode() {
        return reloadMode;
    }

    public CacheLoader<? super K, V> getCacheLoader() {
        return cacheLoader;
    }

    public Supplier<StatsCounter> getStatsCounterSupplier() {
        return statsCounterSupplier;
    }

    public RemovalListener<? super K, ? super V> getRemovalListener() {
        return removalListener;
    }

    public CaffeineCacheBuilder<K, V> expireAfterAccessMillis(long expireAfterAccessMillis) {
        this.expireAfterAccessMillis = expireAfterAccessMillis;
        return this;
    }

    public CaffeineCacheBuilder<K, V> expireAfterWriteMillis(long expireAfterWriteMillis) {
        this.expireAfterWriteMillis = expireAfterWriteMillis;
        return this;
    }

    public CaffeineCacheBuilder<K, V> refreshAfterWriteMillis(long refreshAfterWriteMillis) {
        this.refreshAfterWriteMillis = refreshAfterWriteMillis;
        return this;
    }

    public CaffeineCacheBuilder<K, V> executor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public CaffeineCacheBuilder<K, V> initCapacity(int initCapacity) {
        this.initCapacity = initCapacity;
        return this;
    }

    public CaffeineCacheBuilder<K, V> maximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
        return this;
    }

    public CaffeineCacheBuilder<K, V> reload(ReloadMode reloadMode, CacheLoader<? super K, V> cacheLoader) {
        this.reloadMode = reloadMode;
        this.cacheLoader = cacheLoader;
        return this;
    }

    public CaffeineCacheBuilder<K, V> weakKey(boolean weakKey) {
        this.weakKey = weakKey;
        return this;
    }

    public CaffeineCacheBuilder<K, V> recordStats(Supplier<StatsCounter> statsCounterSupplier) {
        this.statsCounterSupplier = statsCounterSupplier;
        return this;
    }

    public CaffeineCacheBuilder<K, V> removalListener(RemovalListener<? super K, ? super V> removalListener) {
        this.removalListener = removalListener;
        return this;
    }
}
