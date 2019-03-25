package com.github.nicklaus.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http application starter
 *
 * @author weishibai
 * @date 2019/03/24 12:30 PM
 */
@SpringBootApplication(scanBasePackages = "com.nicklaus.http")
public class HttpApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpApplication.class);
    }
}
