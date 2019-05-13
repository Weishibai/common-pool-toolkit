package com.github.nicklaus4.memcached.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.github.nicklaus4.memcached.factory.MemcachedClientFacade;

/**
 * @author weishibai
 * @date 2019/05/13 10:24
 */
@Configuration
@Conditional(MemcachedPoolConditional.class)
public class MemcachedConfiguration {


    @Autowired
    private MemcachedClientBuilder memcachedClientBuilder;

    @Bean
    public MemcachedClientFacade memcachedClientFacade() {
        Objects.requireNonNull(memcachedClientBuilder, "please build memcachedClientBuilder bean");
        return new MemcachedClientFacade(memcachedClientBuilder.build());
    }

}
