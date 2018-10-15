package com.example.user.repositorys;

import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhou.fan
 * 用户访问数据
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据用户名称查询
     * @param userName 用户名称
     * @return User
     */
    @Transactional(readOnly = true ,rollbackFor = Exception.class)
    User findUserByUserName(String userName);
}
