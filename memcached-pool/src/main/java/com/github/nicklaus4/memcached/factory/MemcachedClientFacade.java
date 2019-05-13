package com.github.nicklaus4.memcached.factory;

import net.rubyeye.xmemcached.MemcachedClient;

/**
 * @author weishibai
 * @date 2019/05/12 19:46
 */
public class MemcachedClientFacade {

    private final MemcachedClient memcachedClient;

    public MemcachedClientFacade(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public MemcachedClient memcachedClient() {
        return memcachedClient;
    }

}
