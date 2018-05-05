package com.example.user.repositorys;

import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    User findUserByUserName(String userName);
}
