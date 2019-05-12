package com.github.nicklaus4.localcache.factory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.nicklaus4.localcache.config.CaffeineCacheBuilder;
import com.github.nicklaus4.localcache.model.ReloadMode;

public class LocalCacheFactoryTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void buildSync() {
        final CaffeineCacheBuilder<String, String> builder = CaffeineCacheBuilder.<String, String>newBuilder()
                .expireAfterWriteMillis(1000)
                .expireAfterAccessMillis(1000)
                .initCapacity(100)
                .maximumSize(10_000)
                .reload(ReloadMode.SYNC, String::valueOf);

        final LoadingCache<String, String> loadingCache = LocalCacheFactory.buildSync(builder);
        Assert.assertEquals("weishibai", loadingCache.get("weishibai"));
        Assert.assertEquals("nicklaus", loadingCache.get("nicklaus"));
    }

    @Test
    public void buildAsync() {
        final CaffeineCacheBuilder<String, String> builder = CaffeineCacheBuilder.<String, String>newBuilder()
                .expireAfterWriteMillis(1000)
                .expireAfterAccessMillis(1000)
                .initCapacity(100)
                .maximumSize(10_000)
                .executor(Executors.newSingleThreadExecutor())
                .reload(ReloadMode.ASYNC, key-> {
                    TimeUnit.MILLISECONDS.sleep(10);
                    return key;
                });

        final AsyncLoadingCache<String, String> asyncLoadingCache = LocalCacheFactory.buildAsync(builder);
        final CompletableFuture<String> future = asyncLoadingCache.get("weishibai");
        if (future.isDone()) {
            System.out.println("done");
            try {
                Assert.assertEquals("weishibai", future.get());
            } catch (InterruptedException | ExecutionException e) {
                //
            }
        } else {
            System.out.println("undone");
            Assert.assertEquals("weishibai", asyncLoadingCache.synchronous().get("weishibai"));
        }
    }
}