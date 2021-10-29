package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.entity.User;

public interface UserService {

    User loginCheck(String username, String password);
}
