package com.github.nicklaus4.localcache.factory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.nicklaus4.localcache.config.CaffeineCacheBuilder;
import com.github.nicklaus4.localcache.model.ReloadMode;
import com.google.common.base.Preconditions;

/**
 * @author weishibai
 * @date 2019/05/12 12:43
 */
public class LocalCacheFactory {


    private static <K1, V1> void buildCommon(Caffeine<K1, V1> caffeine, CaffeineCacheBuilder<K1, V1> builder) {
        if (builder.getInitCapacity() > 0) {
            caffeine.initialCapacity(builder.getInitCapacity());
        }

        if (builder.getExpireAfterWriteMillis() > 0) {
            caffeine.expireAfterWrite(builder.getExpireAfterWriteMillis(), TimeUnit.MILLISECONDS);
        }

        if (builder.getExpireAfterAccessMillis() > 0) {
            caffeine.expireAfterAccess(builder.getExpireAfterAccessMillis(), TimeUnit.MILLISECONDS);
        }

        if (builder.getRefreshAfterWriteMillis() > 0) {
            caffeine.refreshAfterWrite(builder.getRefreshAfterWriteMillis(), TimeUnit.MILLISECONDS);
        }

        if (builder.getMaximumSize() > 0) {
            caffeine.maximumSize(builder.getMaximumSize());
        }

        if (builder.getRemovalListener() != null) {
            caffeine.removalListener(builder.getRemovalListener());
        }

        if (builder.getStatsCounterSupplier() != null) {
            caffeine.recordStats(builder.getStatsCounterSupplier());
        }
    }

    public static <K1, V1> LoadingCache<K1, V1> buildSync(CaffeineCacheBuilder<K1, V1> builder) {
        final ReloadMode reloadMode = null == builder.getReloadMode() ? ReloadMode.SYNC : builder.getReloadMode();
        Preconditions.checkArgument(reloadMode == ReloadMode.SYNC, "reload mode should be sync");
        Objects.requireNonNull(builder.getCacheLoader(), "cache loader cannot be null");

        @SuppressWarnings("unchecked")
        final Caffeine<K1, V1> caffeine = (Caffeine<K1, V1>) Caffeine.newBuilder();
        buildCommon(caffeine, builder);
        return caffeine.build(builder.getCacheLoader());
    }

    public static <K1, V1> AsyncLoadingCache<K1, V1> buildAsync(CaffeineCacheBuilder<K1, V1> builder) {
        final ReloadMode reloadMode = null == builder.getReloadMode() ? ReloadMode.SYNC : builder.getReloadMode();
        Preconditions.checkArgument(reloadMode == ReloadMode.ASYNC, "reload mode should be async");
        Objects.requireNonNull(builder.getCacheLoader(), "cache loader cannot be null");
        Objects.requireNonNull(builder.getExecutor(), "executor cannot be null for async mode");

        @SuppressWarnings("unchecked")
        final Caffeine<K1, V1> caffeine = (Caffeine<K1, V1>) Caffeine.newBuilder();
        buildCommon(caffeine, builder);
        caffeine.executor(builder.getExecutor());
        return caffeine.buildAsync(builder.getCacheLoader());
    }

}
