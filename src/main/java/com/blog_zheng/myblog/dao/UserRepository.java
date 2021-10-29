package com.blog_zheng.myblog.dao;

import com.blog_zheng.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// ID data type is Long
public interface UserRepository extends JpaRepository<User, Long> {

    // simple derived query methods following the naming convention
    User findByNameAndPassword(String name, String password);
}
