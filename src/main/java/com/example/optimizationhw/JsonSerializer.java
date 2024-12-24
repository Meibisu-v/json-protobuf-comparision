package com.example.optimizationhw;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonSerializer {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> String toJson(T testDto) throws IOException {
        return MAPPER.writeValueAsString(testDto);
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) throws IOException {
        return MAPPER.readValue(jsonString, clazz);
    }
}
