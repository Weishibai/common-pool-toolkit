package com.github.nicklaus4.http.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author weishibai
 * @date 2019/05/03 11:52 AM
 */
public class ObjectMapperUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperUtils.class);


    public static <T> String toJson(T obj) {
        if (null == obj) {
            return "";
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("to json error: {}", e.getMessage());
            return null;
        }
    }

    public static <T> T fromJson(String jsonStr, Class<T> clz) {
        try {
            return MAPPER.readValue(jsonStr, clz);
        } catch (IOException e) {
            LOGGER.error("from json error: {}", e.getMessage());
            return null;
        }
    }
}
