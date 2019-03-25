package com.github.nicklaus4.http.factory;

import javax.net.ssl.SSLSocketFactory;

import com.github.nicklaus4.http.config.HttpClientDefaultConfigs;
import com.github.nicklaus4.http.config.policy.RequestRejectedPolicy;

/**
 * http client builder
 *
 * @author weishibai
 * @date 2019/03/24 11:10 AM
 */
public class HttpClientBuilder {

    SSLSocketFactory sslSocketFactory = null;

    int maxRequestsPerHost = HttpClientDefaultConfigs.DEFAULT_MAX_REQUESTS_PER_HOST;

    int maxConcurrencyRequests = HttpClientDefaultConfigs.DEFAULT_MAX_CONCURRENCY_REQUESTS;

    int connectTimeoutMillis = HttpClientDefaultConfigs.CONNECT_TIMEOUT_MILLIS;

    int soTimeoutMillis = HttpClientDefaultConfigs.SO_TIMEOUT_MILLIS;

    int connectionPoolMaxIdle = HttpClientDefaultConfigs.DEFAULT_CONNECTION_POOL_MAX_IDLE;

    long connectionPoolKeepAliveDuration = HttpClientDefaultConfigs.DEFAULT_CONNECTION_KEEP_ALIVE_DURATION;

    int maxQueuedSize = HttpClientDefaultConfigs.DEFAULT_MAX_QUEUE_SIZE;

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
