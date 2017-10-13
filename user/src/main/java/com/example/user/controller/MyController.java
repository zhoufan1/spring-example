package com.example.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Value("${nickname}")
    private String nickName;

    @RequestMapping("/hello")
    public String helloWorld() {
        return "hello ,".concat(nickName);
    }


    @RequestMapping("/hello2")
    public String helloWorld2() {
        return "hello ,".concat(nickName);
    }
}
