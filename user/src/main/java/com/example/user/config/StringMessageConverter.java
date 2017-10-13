package com.example.user.config;

import com.alibaba.fastjson.util.IOUtils;
import org.springframework.http.converter.StringHttpMessageConverter;

public final class StringMessageConverter extends StringHttpMessageConverter {
    public static final StringMessageConverter INTANCE = new StringMessageConverter();

    private StringMessageConverter() {
        super(IOUtils.UTF8);
        super.setWriteAcceptCharset(false);
    }
}
