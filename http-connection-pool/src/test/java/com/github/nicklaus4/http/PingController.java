package com.github.nicklaus4.http;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ping url
 *
 * @author weishibai
 * @date 2019/03/24 12:32 PM
 */
@RestController
public class PingController {

    @Autowired
    @Qualifier(value = "defaultOkHttpClient")
    private OkHttpClient defaultOkHttpClient;

    @RequestMapping("/get")
    public String getUrlContent(@RequestParam String url) {
        try {
            final Response response = defaultOkHttpClient.newCall(new Request.Builder()
                    .url(url).build()).execute();

            if (response.isSuccessful()) {
                return response.body().string();
            }
            return "";
        } catch (IOException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        while (true) {

        }
    }
}
