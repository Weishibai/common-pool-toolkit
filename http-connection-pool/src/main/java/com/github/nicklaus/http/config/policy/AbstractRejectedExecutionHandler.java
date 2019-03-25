package com.github.nicklaus.http.config.policy;

import java.io.IOException;
import java.math.BigDecimal;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * abs reject policy handler
 *
 * @author weishibai
 * @date 2019/03/24 11:26 AM
 */
public abstract class AbstractRejectedExecutionHandler implements Interceptor {

    private static final BigDecimal HTTP_QUEUED_RATE = BigDecimal.valueOf(0.5D);

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final Dispatcher dispatcher;

    protected final int maxQueueSize;

    AbstractRejectedExecutionHandler(Dispatcher dispatcher, int maxQueueSize) {
        this.dispatcher = dispatcher;
        this.maxQueueSize = maxQueueSize;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final BigDecimal queuedCount = BigDecimal.valueOf(dispatcher.queuedCallsCount());
        final BigDecimal runningCount = BigDecimal.valueOf(dispatcher.runningCallsCount());
        if (queuedCount.divide(runningCount.add(runningCount), BigDecimal.ROUND_HALF_DOWN)
                .compareTo(HTTP_QUEUED_RATE) > 0) {
            logger.warn("http queue is exceeding rate");
        }
        rejectedExecution(chain, dispatcher, maxQueueSize);
        return chain.proceed(chain.request());
    }

    abstract void rejectedExecution(@Nonnull Chain chain, Dispatcher dispatcher, int maxQueueSize);
}
