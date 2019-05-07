package com.github.nicklaus4.http.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.github.nicklaus4.http.factory.HttpClientBuilder;
import com.github.nicklaus4.http.factory.HttpClientFactory;

import okhttp3.OkHttpClient;

/**
 * http spring configuration
 *
 * @author weishibai
 * @date 2019/03/24 12:24 PM
 */
@Configuration
public class HttpConfiguration {

    @Bean
    @Conditional(DefaultHttpCondition.class)
    public OkHttpClient defaultOkHttpClient() {
        return HttpClientFactory.create(HttpClientBuilder.newBuilder());
    }

    @Bean
    @Conditional(DefaultHttpCondition.class)
    public DefaultHttpParseService httpParseService() {
        return new DefaultHttpParseService();
    }
}
