package com.example.authority.feign;

import com.example.authority.dto.UserResponse;
import com.example.authority.dto.request.LoginRequest;
import com.example.authority.dto.response.LoginResponse;
import com.example.foundation.dto.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhoufan
 * @date 2019/6/13 09:56
 * @description
 */
@FeignClient("user-service")
@RequestMapping("/user")
public interface UserClient {

    @RequestMapping("/users/login")
    Response<LoginResponse> login(@RequestBody LoginRequest request);

    @RequestMapping("userInfo/findUserByUserName")
    Response<UserResponse> findUserByUserName(@RequestParam("userName") String userName);
}
