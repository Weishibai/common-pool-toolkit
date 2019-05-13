package com.github.nicklaus4.http.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.github.nicklaus4.http.factory.HttpClientBuilder;
import com.github.nicklaus4.http.factory.HttpClientFactory;
import com.github.nicklaus4.http.reactive.ReactiveHttpClient;

import okhttp3.OkHttpClient;

/**
 * http spring configuration
 *
 * @author weishibai
 * @date 2019/03/24 12:24 PM
 */
@Configuration
@Conditional(DefaultHttpCondition.class)
public class HttpConfiguration {

    @Bean
    public OkHttpClient defaultOkHttpClient() {
        return HttpClientFactory.create(HttpClientBuilder.newBuilder());
    }

    @Bean
    public DefaultHttpParseService httpParseService() {
        return new DefaultHttpParseService();
    }

    @Bean
    public ReactiveHttpClient reactiveHttpClient() {
        return new ReactiveHttpClient();
    }
}
