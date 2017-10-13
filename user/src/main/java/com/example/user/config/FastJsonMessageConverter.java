package com.example.user.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.IOUtils;
import com.example.user.common.DataFormat;
import com.google.common.collect.Lists;
import org.springframework.http.MediaType;

public final class FastJsonMessageConverter extends FastJsonHttpMessageConverter {
    public static final FastJsonHttpMessageConverter INSTALL = new FastJsonHttpMessageConverter();

    private FastJsonMessageConverter() {
        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(IOUtils.UTF8);
        config.setDateFormat(DataFormat.DatetimeFormat.name());
        config.setSerializerFeatures(SerializerFeature.SkipTransientField,
                SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteDateUseDateFormat);
        super.setDefaultCharset(IOUtils.UTF8);
        super.setFastJsonConfig(config);
        super.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8,
                MediaType.APPLICATION_JSON));
    }

}
