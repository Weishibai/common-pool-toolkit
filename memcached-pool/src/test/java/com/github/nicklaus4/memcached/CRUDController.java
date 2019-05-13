package com.github.nicklaus4.memcached;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nicklaus4.memcached.factory.MemcachedClientFacade;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * crud controller
 *
 * @author weishibai
 * @date 2019/03/19 4:12 PM
 */
@RestController
public class CRUDController {

    @Autowired
    private MemcachedClientFacade memcachedClientFacade;

    @RequestMapping("/get/{key}")
    public void getKey(@PathVariable String key) {
        final MemcachedClient memcachedClient = memcachedClientFacade.memcachedClient();
        try {
            memcachedClient.set(key, 50, "key: " + key);
            String val = memcachedClient.get(key);
            System.out.println(val);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            e.printStackTrace();
        }
    }
}
