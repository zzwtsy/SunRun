package cn.zzwtsy.sunrun.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode fromJson(String content) throws JsonProcessingException {
        return MAPPER.readTree(content);
    }
}
