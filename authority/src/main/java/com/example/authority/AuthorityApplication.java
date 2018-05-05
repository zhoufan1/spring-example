package com.example.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = "com.example.authority.model")
public class AuthorityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorityApplication.class, args);
    }
}
