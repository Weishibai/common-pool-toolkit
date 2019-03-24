package com.nicklaus.http.factory;

import static com.nicklaus.http.config.HttpClientDefaultConfigs.CONNECT_TIMEOUT_MILLIS;
import static com.nicklaus.http.config.HttpClientDefaultConfigs.DEFAULT_CONNECTION_KEEP_ALIVE_DURATION;
import static com.nicklaus.http.config.HttpClientDefaultConfigs.DEFAULT_CONNECTION_POOL_MAX_IDLE;
import static com.nicklaus.http.config.HttpClientDefaultConfigs.DEFAULT_MAX_CONCURRENCY_REQUESTS;
import static com.nicklaus.http.config.HttpClientDefaultConfigs.DEFAULT_MAX_QUEUE_SIZE;
import static com.nicklaus.http.config.HttpClientDefaultConfigs.DEFAULT_MAX_REQUESTS_PER_HOST;
import static com.nicklaus.http.config.HttpClientDefaultConfigs.SO_TIMEOUT_MILLIS;

import javax.net.ssl.SSLSocketFactory;

import com.nicklaus.http.config.policy.RequestRejectedPolicy;

/**
 * http client builder
 *
 * @author weishibai
 * @date 2019/03/24 11:10 AM
 */
public class HttpClientBuilder {

    SSLSocketFactory sslSocketFactory = null;

    int maxRequestsPerHost = DEFAULT_MAX_REQUESTS_PER_HOST;

    int maxConcurrencyRequests = DEFAULT_MAX_CONCURRENCY_REQUESTS;

    int connectTimeoutMillis = CONNECT_TIMEOUT_MILLIS;

    int soTimeoutMillis = SO_TIMEOUT_MILLIS;

    int connectionPoolMaxIdle = DEFAULT_CONNECTION_POOL_MAX_IDLE;

    long connectionPoolKeepAliveDuration = DEFAULT_CONNECTION_KEEP_ALIVE_DURATION;

    int maxQueuedSize = DEFAULT_MAX_QUEUE_SIZE;

    RequestRejectedPolicy rejectedPolicy = RequestRejectedPolicy.DISCARD;

    String proxy;

    public static HttpClientBuilder newBuilder() {
        return new HttpClientBuilder();
    }

    public HttpClientBuilder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    public HttpClientBuilder connectTimeoutMillis(int connTimeOutMillis) {
        this.connectTimeoutMillis = connTimeOutMillis;
        return this;
    }

    public HttpClientBuilder soTimeoutMillis(int soTimeoutMillis) {
        this.soTimeoutMillis = soTimeoutMillis;
        return this;
    }

    public HttpClientBuilder connectionPoolMaxIdle(int connectionPoolMaxIdle) {
        this.connectionPoolMaxIdle = connectionPoolMaxIdle;
        return this;
    }

    public HttpClientBuilder connectionPoolKeepAliveDuration(int durationMillis) {
        this.connectionPoolKeepAliveDuration = durationMillis;
        return this;
    }

    public HttpClientBuilder maxRequestsPerHost(int maxRequestsPerHost) {
        this.maxRequestsPerHost = maxRequestsPerHost;
        return this;
    }

    public HttpClientBuilder maxConcurrencyRequests(int maxConcurrencyRequests) {
        this.maxConcurrencyRequests = maxConcurrencyRequests;
        return this;
    }

    public HttpClientBuilder rejectedPolicy(RequestRejectedPolicy policy) {
        this.rejectedPolicy = policy;
        return this;
    }

    /* proxy relevant */
    public HttpClientBuilder proxy(String proxy) {
        this.proxy = proxy;
        return this;
    }
}
