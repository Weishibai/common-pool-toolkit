package com.github.nicklaus4.memcached;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author weishibai
 * @date 2019/05/13 10:43
 */
@SpringBootApplication(scanBasePackages = "com.github.nicklaus4.memcached")
public class AppStarter {


    public static void main(String[] args) {
        SpringApplication.run(AppStarter.class);
    }
}
