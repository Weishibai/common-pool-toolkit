package com.nicklaus.http.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * default http condition
 *
 * @author weishibai
 * @date 2019/03/24 12:26 PM
 */
public class DefaultHttpCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.valueOf(context.getEnvironment().getProperty("okhttp-default-enabled"));
    }
}
