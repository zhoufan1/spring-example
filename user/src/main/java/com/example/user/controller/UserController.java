package com.example.user.controller;

import com.example.user.model.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll() {
        List<User> all = userService.findAll();
        return all;
    }

    @RequestMapping("/findUserByUserName")
    public User findUserByUserName(@RequestParam("userName") String userName){
        return userService.findUserByUserName(userName);
    }

    @PostMapping("/welcome")
    public String welcome(@RequestParam("name") String name){
        return userService.welcome(name);

    }

    @PostMapping("/test1")
    public Map<String,String> test() {
        return userService.test1();
    }
}
