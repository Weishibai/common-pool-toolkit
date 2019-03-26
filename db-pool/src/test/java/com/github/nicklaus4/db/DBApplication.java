package com.github.nicklaus4.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * db app
 *
 * @author weishibai
 * @date 2019/03/19 3:32 PM
 */
@SpringBootApplication(scanBasePackages = "com.github.nicklaus4.db")
public class DBApplication {

    public static void main(String[] args) {
        SpringApplication.run(DBApplication.class);
    }
}
