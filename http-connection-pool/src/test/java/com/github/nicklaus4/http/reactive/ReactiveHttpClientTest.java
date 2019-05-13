package com.github.nicklaus4.http.reactive;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ReactiveHttpClientTest {

    @Test
    public void asyncGet() {
        ReactiveHttpClient httpClient = new ReactiveHttpClient();
        System.out.println(httpClient.asyncGet("https://www.baidu.com", String.class).block());
    }

    @Test
    public void testMultiGet() {
        ReactiveHttpClient httpClient = new ReactiveHttpClient();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                System.out.println(httpClient.asyncGet("https://www.baidu.com", String.class).block());
                latch.countDown();
            });
        }

        try {
            latch.await(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //ig
        }
    }
}