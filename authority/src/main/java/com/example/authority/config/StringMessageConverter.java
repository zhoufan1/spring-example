package com.example.authority.config;

import com.alibaba.fastjson.util.IOUtils;
import org.springframework.http.converter.StringHttpMessageConverter;

public final class StringMessageConverter extends StringHttpMessageConverter {
    public static final StringMessageConverter INSTANCE = new StringMessageConverter();

    private StringMessageConverter() {
        super(IOUtils.UTF8);
        super.setWriteAcceptCharset(false);
    }
}
