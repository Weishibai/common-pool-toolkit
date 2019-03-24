package com.nicklaus.http.config;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * default configs
 *
 * @author weishibai
 * @date 2019/03/24 11:11 AM
 */
public class HttpClientDefaultConfigs {

    public static final int CONNECT_TIMEOUT_MILLIS = (int) SECONDS.toMillis(15);

    public static final int SO_TIMEOUT_MILLIS = (int) SECONDS.toMillis(20);

    public static final int DEFAULT_MAX_CONCURRENCY_REQUESTS = 64;

    public static final int DEFAULT_MAX_REQUESTS_PER_HOST = 64;

    public static final int DEFAULT_CONNECTION_POOL_MAX_IDLE = 5;

    public static final long DEFAULT_CONNECTION_KEEP_ALIVE_DURATION = MINUTES.toMillis(5);

    public static final int DEFAULT_MAX_QUEUE_SIZE = Integer.MAX_VALUE;
}
