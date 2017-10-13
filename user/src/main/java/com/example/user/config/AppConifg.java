package com.example.user.config;

import com.google.common.collect.Lists;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.util.List;

public class AppConifg extends WebMvcConfigurationSupport {

    private final List<HttpMessageConverter<?>> CONVERTERS = Lists.newArrayList(StringMessageConverter.INTANCE,
            FastJsonMessageConverter.INSTALL);

    private ObjectFactory<HttpMessageConverters> feignconverter = () -> new HttpMessageConverters(CONVERTERS);


    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(converters);
        super.configureMessageConverters(converters);
    }


    /**
     * 禁用 Feign 调用出错重试
     **/
    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignconverter));
    }

    @Bean
    public Encoder encoder() {
        return new SpringEncoder(feignconverter);
    }
}
