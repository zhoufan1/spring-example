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
        User userByUserName = userService.findUserByUserName(userName);
        return userByUserName;
    }

    @PostMapping("/welcome")
    public String welcome(@RequestParam("name") String name){
        String welcome = userService.welcome(name);
        return welcome;

    }

    @PostMapping("/test1")
    public Map<String,String> test() {
        return userService.test1();
    }
}
