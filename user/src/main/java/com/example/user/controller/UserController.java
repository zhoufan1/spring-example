package com.example.user.controller;

import com.example.foundation.dto.Response;
import com.example.user.dto.response.UserResponse;
import com.example.user.model.User;
import com.example.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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

    @PostMapping("/login")
    public Response<UserResponse> login(@RequestBody User user) {
        User userResult = userService.login(user);
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userResult,response);
        return Response.success(response);
    }
}
