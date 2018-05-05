package com.example.user.service;

import com.example.user.client.AuthorityClient;
import com.example.user.model.User;
import com.example.user.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityClient authorityClient;

    public String welcome(String name){
        String welcome = authorityClient.helloWorld(name);
        return welcome;
    };

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }
}
