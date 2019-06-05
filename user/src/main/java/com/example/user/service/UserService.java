package com.example.user.service;

import com.example.user.client.AuthorityClient;
import com.example.user.model.User;
import com.example.user.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author zhou.fan
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityClient authorityClient;

    public String welcome(String name) {
        return authorityClient.helloWorld(name);
    }

    public Map<String, String> test1() {
        return authorityClient.test();
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    public User login(User user) {
        return userRepository.findUserByUserNameAndUserPass(user.getUserName(),user.getUserPass());
    }
}
