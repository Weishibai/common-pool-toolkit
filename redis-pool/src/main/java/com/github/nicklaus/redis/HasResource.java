package com.github.nicklaus.redis;

/**
 * has resources
 *
 * @author weishibai
 * @date 2019/03/19 6:57 PM
 */
@FunctionalInterface
public interface HasResource<T> {

    T getResource();
}
