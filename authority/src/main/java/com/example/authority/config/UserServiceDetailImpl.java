package com.example.authority.config;

import com.alibaba.fastjson.JSON;
import com.example.authority.dto.UserResponse;
import com.example.authority.feign.UserClient;
import com.example.foundation.dto.Response;
import com.example.foundation.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ZhouFan
 * @date 2019/10/25 16:33
 * @description
 */
@Service
@Slf4j
public class UserServiceDetailImpl implements UserDetailsService {
    @Autowired
    private UserClient userClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load username :{}",username);
        Response<UserResponse> user = userClient.findUserByUserName(username);
        if (!user.isSuccess()) {
            throw new BusinessException(user);
        }
        log.info("response:{}", JSON.toJSONString(user));
        return new UserInfo(user.getData());
    }
}
