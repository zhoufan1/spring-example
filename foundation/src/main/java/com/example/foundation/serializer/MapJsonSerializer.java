package com.example.foundation.serializer;

import com.example.foundation.utils.CollectionUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.Map;

/**
 * @author yangsen
 * @ClassName: ArrayJsonSerializer
 * @Description: TODO
 * @date 2017年8月2日 下午2:41:38
 */
public class MapJsonSerializer extends JsonSerializer<Map> {
    private final Class<?>[] clazz = {Integer.class, Double.class, Float.class, Long.class, Short.class, Byte.class, Boolean.class, Character.class};

    public final static MapJsonSerializer instance = new MapJsonSerializer();


    public void serialize(Map value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        generator.writeStartObject();
        if (!CollectionUtils.isNullOrEmpty(value)) {
            for (Object key : value.keySet()) {
                if (value.get(key) != null) {
                    if (ArrayUtils.contains(clazz, value.get(key).getClass())) {
                        generator.writeObjectField(key.toString(), value.get(key).toString());
                    } else {
                        generator.writeObjectField(key.toString(), value.get(key));
                    }
                } else {
                    generator.writeObjectField(key.toString(), StringUtils.EMPTY);
                }
            }
        }
        generator.writeEndObject();
    }
}
