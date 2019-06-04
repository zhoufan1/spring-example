package com.example.foundation.web;

import com.example.foundation.authority.AuthorityInterceptor;
import com.example.foundation.authority.AuthorityResolver;
import com.example.foundation.dto.Response;
import com.example.foundation.exception.BusinessException;
import com.example.foundation.web.converters.FastJsonMessageConverter;
import com.example.foundation.web.converters.FormMessageConverter;
import com.example.foundation.web.converters.StringMessageConverter;
import com.google.common.collect.Lists;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.List;

/**
 * @author zhoufan
 * @date 2019/6/4 20:07
 * @description
 */
@Configurable
public abstract class WebConfigurationSupport extends WebMvcConfigurationSupport {

    private static final List<HttpMessageConverter<?>> CONVERTERS = Lists.newArrayList(FastJsonMessageConverter.INSTANCE,
            StringMessageConverter.INSTANCE, FormMessageConverter.INSTANCE);

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorityInterceptor());
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.addAll(CONVERTERS);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthorityResolver());
    }

    @Configuration
    public static class FeignConfiguration {
        private ObjectFactory<HttpMessageConverters> feignConverters = () -> new HttpMessageConverters(CONVERTERS);

        /**
         * 禁用 Feign 调用出错重试
         **/
        @Bean
        public Retryer retryer() {
            return Retryer.NEVER_RETRY;
        }

        @Bean
        public Decoder feignDecoder() {
            return new ResponseEntityDecoder(new SpringDecoder(this.feignConverters));
        }

        @Bean
        public Encoder feignEncoder() {
            return new SpringEncoder(this.feignConverters);
        }
    }

    @ControllerAdvice
    @ResponseBody
    @Slf4j
    public static class GlobalControllerHandler implements ResponseBodyAdvice<Object> {
        @ExceptionHandler(Exception.class)
        public Response exceptionHandle(Exception e) {
            log.error("process system error,message:{} ", e.getMessage());
            return Response.systemError(e.getMessage());
        }

        @ExceptionHandler(BusinessException.class)
        public Response exceptionHandle(BusinessException e) {
            log.error("process business error,message:{} ", e.getMessage());
            return Response.failed(e.getCode(), e.getMessage());
        }


        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

            return o;
        }
    }


}
