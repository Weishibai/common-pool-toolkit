package com.github.nicklaus4.http.factory;

import static com.google.common.net.HostAndPort.fromString;
import static java.net.Proxy.Type.HTTP;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.net.HostAndPort;
import com.github.nicklaus4.http.config.policy.DiscardRejectedPolicy;
import com.github.nicklaus4.http.config.policy.RequestRejectedPolicy;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * http client factory
 *
 * @author weishibai
 * @date 2019/03/24 11:21 AM
 */
public class HttpClientFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpClientFactory.class);

    public static OkHttpClient create(HttpClientBuilder builder) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(Math.max(builder.maxConcurrencyRequests, builder.maxRequestsPerHost));
        dispatcher.setMaxRequestsPerHost(builder.maxRequestsPerHost);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(builder.connectTimeoutMillis, TimeUnit.MILLISECONDS)
                .readTimeout(builder.soTimeoutMillis, TimeUnit.MILLISECONDS)
                .writeTimeout(builder.soTimeoutMillis, TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(builder.connectionPoolMaxIdle,
                        builder.connectionPoolKeepAliveDuration, MILLISECONDS))
                .dispatcher(dispatcher);

        /* ssl */
        if (null != builder.sslSocketFactory) {
            okHttpBuilder.sslSocketFactory(builder.sslSocketFactory);
        }

        /* add proxy */
        if (StringUtils.isNotBlank(builder.proxy)) {
            okHttpBuilder.proxySelector(createStaticHttpProxy(builder.proxy));
        }

        /* add reject policy */
        if (builder.rejectedPolicy == RequestRejectedPolicy.DISCARD) {
            okHttpBuilder.addInterceptor(new DiscardRejectedPolicy(dispatcher, builder.maxQueuedSize));
        }
        return okHttpBuilder.build();
    }

    private static ProxySelector createStaticHttpProxy(String address) {
        HostAndPort hostAndPort = fromString(address);
        SocketAddress sa = new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort());
        Proxy proxy = new Proxy(HTTP, sa);
        return new ProxySelector() {

            @Override
            public List<Proxy> select(URI uri) {
                return Collections.singletonList(proxy);
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa1, IOException ioe) {
                LOGGER.warn("proxy connection failed uri={}", uri, ioe);
            }
        };
    }

}
