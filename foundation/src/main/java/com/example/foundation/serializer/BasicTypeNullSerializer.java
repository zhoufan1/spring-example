package com.example.foundation.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;

public class BasicTypeNullSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (null == value) {
        	if (value instanceof String) {
        		gen.writeString(StringUtils.EMPTY);
			}else{
				gen.writeNull();
			}
        	
           
        } else {
            gen.writeObject(value);
        }
    }
}
