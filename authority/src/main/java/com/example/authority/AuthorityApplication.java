package com.example.authority;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.feign.*;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }
}
