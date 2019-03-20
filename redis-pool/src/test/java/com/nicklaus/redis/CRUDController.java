package com.nicklaus.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicklaus.redis.wrapper.JedisPoolFacade;

import redis.clients.jedis.JedisCommands;

/**
 * crud controller
 *
 * @author weishibai
 * @date 2019/03/19 4:12 PM
 */
@RestController
public class CRUDController {

    @Autowired
    private JedisPoolFacade poolFacade;

    @RequestMapping("/get/{key}")
    public void getKey(@PathVariable String key) {
        final JedisCommands jedis = poolFacade.getResource();
        System.out.println(jedis.get(key));
    }
}
