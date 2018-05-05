package com.example.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class MyController {


    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public HashMap helloWorld() {
        return new HashMap(){
            {
                put("nickName", "aa");
            }
        };
    }


}
