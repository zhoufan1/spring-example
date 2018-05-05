package com.example.authority.controller;

import javafx.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @PostMapping("/hello")
    public String helloWorld(@RequestParam("nickName") String nickName) {
        return "hello ,".concat(nickName);
    }

    @PostMapping("/test1")
    public Pair<String,String> test(){
        return new Pair<>("xxx", "nnn");
    }
}
