package com.thoughtworks.solomonsmines.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    public static boolean isObjectJson(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(str).isObject();
        } catch (IOException e) {
            return false;
        }
    }
}
