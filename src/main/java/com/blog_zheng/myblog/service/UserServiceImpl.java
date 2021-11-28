package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.dao.UserRepository;
import com.blog_zheng.myblog.entity.User;
import com.blog_zheng.myblog.utils.MD5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    // use constructor injection to inject dependencies
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param name     the admin user login name
     * @param password the password
     * @return A User if the login name and the password are both correct, null otherwise
     */
    @Override
    public User loginCheck(String name, String password) {
        // query the database
        User user = userRepository.findByNameAndPassword(name, MD5Encrypt.encrypt(password));
        return user;
    }
}
