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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class AppConfig extends WebMvcConfigurationSupport {

    private final List<HttpMessageConverter<?>> CONVERTERS = Lists.newArrayList(StringMessageConverter.INSTANCE,
            FastJsonMessageConverter.INSTANCE, FormMessageConverter.INSTANCE);

    private ObjectFactory<HttpMessageConverters> feignConverters;

    @PostConstruct
    public void initConfiguration() {
        feignConverters = () -> new HttpMessageConverters(CONVERTERS);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.addAll(CONVERTERS);
    }

    /**
     * 禁用 Feign 调用出错重试
     **/
    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    @Primary
    public Decoder decoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignConverters));
    }

    @Bean
    @Primary
    public Encoder encoder() {
        return new SpringEncoder(feignConverters);
    }
}
