package com.example.foundation.web;

import com.example.foundation.authority.AuthorityInterceptor;
import com.example.foundation.authority.AuthorityResolver;
import com.example.foundation.authority.AuthoritySession;
import com.example.foundation.dto.Response;
import com.example.foundation.exception.BusinessException;
import com.example.foundation.web.converters.FastJsonMessageConverter;
import com.example.foundation.web.converters.FormMessageConverter;
import com.example.foundation.web.converters.StringMessageConverter;
import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.cloud.netflix.hystrix.HystrixAutoConfiguration;
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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoufan
 * @date 2019/6/4 20:07
 */
@Configuration
public class WebConfigurationSupport extends WebMvcConfigurationSupport {

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
    @ConditionalOnBean(FeignAutoConfiguration.class)
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

        @Bean
        public RequestInterceptor handlerInterceptor() {
            return requestTemplate -> {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes == null) {
                    return;
                }
                HttpServletRequest request = requestAttributes.getRequest();
                String authorityHeader = request.getHeader(AuthoritySession.X_Authority_Header);
                if (StringUtils.isNotBlank(authorityHeader)) {
                    requestTemplate.header(AuthoritySession.X_Authority_Header, authorityHeader);
                }
            };

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
            return Response.failed(e.getCode());
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

    @Configuration
    @Slf4j
    @ConditionalOnBean(HystrixAutoConfiguration.class)
    public static class HystrixConfiguration extends HystrixConcurrencyStrategy {
        private HystrixConcurrencyStrategy strategy;

        public HystrixConfiguration() {
            HystrixPlugins instance = HystrixPlugins.getInstance();
            strategy = instance.getConcurrencyStrategy();
            if (strategy instanceof HystrixConfiguration) {
                return;
            }
            HystrixCommandExecutionHook commandExecutionHook = instance.getCommandExecutionHook();
            HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
            HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
            HystrixPropertiesStrategy propertiesStrategy =
                    HystrixPlugins.getInstance().getPropertiesStrategy();
            this.logCurrentStateOfHystrixPlugins(eventNotifier, metricsPublisher, propertiesStrategy);
            HystrixPlugins.reset();
            HystrixPlugins.getInstance().registerConcurrencyStrategy(this);
            HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
            HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
            HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
            HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);

        }

        private void logCurrentStateOfHystrixPlugins(HystrixEventNotifier eventNotifier,
                                                     HystrixMetricsPublisher metricsPublisher, HystrixPropertiesStrategy propertiesStrategy) {
            if (log.isDebugEnabled()) {
                log.debug("Current Hystrix plugins configuration is [" + "concurrencyStrategy ["
                        + this.strategy + "]," + "eventNotifier [" + eventNotifier + "]," + "metricPublisher ["
                        + metricsPublisher + "]," + "propertiesStrategy [" + propertiesStrategy + "]," + "]");
                log.debug("Registering Sleuth Hystrix Concurrency Strategy.");
            }
        }

        @Override
        public <T> Callable<T> wrapCallable(Callable<T> callable) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            return new WrappedCallable<>(callable, requestAttributes);

        }

        static class WrappedCallable<T> implements Callable<T> {
            private final Callable<T> target;
            private final RequestAttributes requestAttributes;

            public WrappedCallable(Callable<T> target, RequestAttributes requestAttributes) {
                this.target = target;
                this.requestAttributes = requestAttributes;
            }

            @Override
            public T call() throws Exception {
                try {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    return target.call();
                } finally {
                    RequestContextHolder.resetRequestAttributes();
                }
            }
        }

        @Override
        public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                                HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize,
                                                HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            return this.strategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime,
                    unit, workQueue);
        }

        @Override
        public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                                HystrixThreadPoolProperties threadPoolProperties) {
            return this.strategy.getThreadPool(threadPoolKey, threadPoolProperties);
        }

        @Override
        public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
            return this.strategy.getBlockingQueue(maxQueueSize);
        }

        @Override
        public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
            return this.strategy.getRequestVariable(rv);
        }
    }
}