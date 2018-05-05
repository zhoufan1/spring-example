package com.example.authority.config;

import com.google.common.collect.Lists;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.List;

public class FormMessageConverter extends FormHttpMessageConverter {
    public static FormMessageConverter INSTANCE = new FormMessageConverter();
    public FormMessageConverter(){
        setCharset(Charset.forName("UTF-8"));
        List<HttpMessageConverter<?>> partConverters = Lists.newArrayList();
        partConverters.add(new ByteArrayHttpMessageConverter());
        partConverters.add(StringMessageConverter.INSTANCE);
        partConverters.add(new SourceHttpMessageConverter());
        partConverters.add(FastJsonMessageConverter.INSTANCE);
        super.setPartConverters(partConverters);
    }
}
