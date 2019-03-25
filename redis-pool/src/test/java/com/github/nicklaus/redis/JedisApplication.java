package com.github.nicklaus.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * db app
 *
 * @author weishibai
 * @date 2019/03/19 3:32 PM
 */
@SpringBootApplication(scanBasePackages = "com.nicklaus.redis")
public class JedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JedisApplication.class);
    }
}
