package com.ie.forecast.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static String asJsonString(final Object parameter) {
        try {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(parameter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
