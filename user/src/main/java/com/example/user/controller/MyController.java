package com.example.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
//    @Value("${nickname}")
    private String nickName="111";

    @RequestMapping("/hello")
    public String helloWorld() {
        return "hello ,".concat(nickName);
    }
}
