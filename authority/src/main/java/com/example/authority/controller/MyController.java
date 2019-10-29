package com.example.authority.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.SecureRandom;
import java.util.HashMap;

@RestController
public class MyController {

    @PostMapping("/hello")
    public String helloWorld(@RequestParam("nickName") String nickName) {
        return "hello ,".concat(nickName);
    }

    @PostMapping("/test1")
    public HashMap test(){
        SecureRandom random = new SecureRandom();
        random.nextInt();
        return new HashMap(1) {
            {
                put("xx", "xx");
            }
        };
    }
}
