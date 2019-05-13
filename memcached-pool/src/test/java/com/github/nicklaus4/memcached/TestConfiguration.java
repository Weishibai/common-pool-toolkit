package com.github.nicklaus4.memcached;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.github.nicklaus4.memcached.config.MemcachedClientBuilder;
import com.github.nicklaus4.memcached.config.MemcachedConfiguration;

import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;

/**
 * @author weishibai
 * @date 2019/05/13 10:34
 */
@Configuration
@Import(MemcachedConfiguration.class)
public class TestConfiguration {

    @Bean
    public MemcachedClientBuilder memcachedClientBuilder() {
        return MemcachedClientBuilder.newBuilder()
                .addressList(Collections.singletonList("localhost:11211"))
                .connectionPoolSize(1)
                .connTimeoutMillis(5000)
                .opTimeoutMillis(5000)
                .compressionThreshold(1024)
                .name("demo-client1")
                .maxQueuedNoReplyOperations(10000)
                .commandFactory(new BinaryCommandFactory())
                .sessionLocator(new KetamaMemcachedSessionLocator());
    }

}
