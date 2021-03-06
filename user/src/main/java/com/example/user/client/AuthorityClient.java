package com.example.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * fegin 服务之间的通信
 *
 * @FeignClient 为对方服务的${spring.application.name}名称
 */
@FeignClient("authority-service")
public interface AuthorityClient {


    @PostMapping("/hello")
    String helloWorld(@RequestParam("nickName") String nickName);

    @PostMapping("/test1")
    Map<String, String> test();
}
