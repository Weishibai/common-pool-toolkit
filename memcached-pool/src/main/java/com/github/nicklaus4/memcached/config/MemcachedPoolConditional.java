package com.github.nicklaus4.memcached.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * jedis conditional
 *
 * @author weishibai
 * @date 2019/03/20 10:54 AM
 */
public class MemcachedPoolConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.valueOf(context.getEnvironment().getProperty("memcached-pool-enabled"));
    }
}
