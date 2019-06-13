package com.example.user.controller;

import com.example.foundation.dto.Response;
import com.example.foundation.exception.BusinessException;
import com.example.user.dto.BusinessCode;
import com.example.user.dto.response.UserResponse;
import com.example.user.model.User;
import com.example.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoufan
 * @date 2019/6/13 10:08
 * @description
 */
@RestController
@RequestMapping("/authorityInfo")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response<UserResponse> login(@RequestBody User user) {
        User login = userService.login(user);
        if (login == null) {
            throw new BusinessException(BusinessCode.AuthorityAccount);
        }
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return Response.success(response);
    }
}
