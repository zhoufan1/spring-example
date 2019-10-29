package com.example.foundation.utils;

import com.example.foundation.serializer.ArrayJsonSerializer;
import com.example.foundation.serializer.BeanSerializer;
import com.example.foundation.serializer.MapJsonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Slf4j
public final class JacksonUtils {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static TypeFactory TYPEFACTORY = MAPPER.getTypeFactory();

    private JacksonUtils() {

    }

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Number.class, ToStringSerializer.instance);
        module.addSerializer(Boolean.class, ToStringSerializer.instance);
        module.addSerializer(int[].class, ArrayJsonSerializer.INSTANCE);
        module.addSerializer(Map.class, MapJsonSerializer.INSTANCE);
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        MAPPER.setSerializerFactory(MAPPER.getSerializerFactory().withSerializerModifier(new BeanSerializer()));
        MAPPER.registerModule(module);
        MAPPER.setDateFormat(new SimpleDateFormat(DataFormatEnum.StrikeDateTime.realVal()));
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper instance() {
        return MAPPER;
    }

    public static TypeFactory getTypeFactory() {
        return TYPEFACTORY;
    }

    public static String toJSONString(Object o) {
        try {
            return MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public static <T> T parseObject(Class<T> type, String json) {

        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseCollection(Class<T> type, String json) {
        try {
            CollectionType collectionType = getTypeFactory().constructCollectionType(List.class, type);
            return MAPPER.readValue(json, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T[] parseArray(Class<T> type, String json) {
        try {
            ArrayType collectionType = getTypeFactory().constructArrayType(type);
            return MAPPER.readValue(json, collectionType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
