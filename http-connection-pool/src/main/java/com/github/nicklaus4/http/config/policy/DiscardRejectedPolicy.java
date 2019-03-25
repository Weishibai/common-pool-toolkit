package com.github.nicklaus4.http.config.policy;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.collections4.CollectionUtils;

import okhttp3.Call;
import okhttp3.Dispatcher;

/**
 * discard policy while rejected
 *
 * @author weishibai
 * @date 2019/03/24 11:47 AM
 */
public class DiscardRejectedPolicy extends AbstractRejectedExecutionHandler {

    public DiscardRejectedPolicy(Dispatcher dispatcher, int maxQueuedSize) {
        super(dispatcher, maxQueuedSize);
    }

    @Override
    public void rejectedExecution(@Nonnull Chain chain, Dispatcher dispatcher, int maxQueueSize) {
        List<Call> calls = dispatcher.queuedCalls();
        if (CollectionUtils.isNotEmpty(calls) && calls.size() > maxQueueSize) {
            Call call = findCancelCall(calls, calls.size() - 1);
            if (call != null) {
                call.cancel();
                logger.error("last request '" + call.request().url() + "' rejected from full queue: " + maxQueueSize);
            }
        }
    }

    @Nullable
    private Call findCancelCall(List<Call> calls, int cursor) {
        if (cursor >= 0) {
            Call call = calls.get(cursor);
            if (!call.isCanceled()) {
                return call;
            }
            return findCancelCall(calls, --cursor);
        }
        return null;
    }
}
