package com.github.nicklaus4.http.config;

import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nicklaus4.http.utils.ObjectMapperUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author weishibai
 * @date 2019/05/07 16:18
 */
public class DefaultHttpParseService {


    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHttpParseService.class);

    @Resource
    private OkHttpClient defaultOkHttpClient;

    public <T> T post(String url, String jsonReq, Class<T> clz) {
        @SuppressWarnings("ConstantConditions")
        RequestBody body = RequestBody.create(JSON, jsonReq);
        final Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            final Response response = defaultOkHttpClient.newCall(httpRequest).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String rawJsonResp = response.body().string();  //only once
            LOGGER.info("original http response: {}", rawJsonResp);
            return ObjectMapperUtils.fromJson(rawJsonResp, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T get(String url, Class<T> clz) {
        try {
            final Response response = defaultOkHttpClient.newCall(new Request.Builder()
                    .url(url).build()).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            String rawJsonResp = response.body().string();  //only once
            LOGGER.info("original http response: {}", rawJsonResp);
            return ObjectMapperUtils.fromJson(rawJsonResp, clz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
