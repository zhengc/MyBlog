package com.blog_zheng.myblog.controller;

import com.blog_zheng.myblog.customExceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        // This line is to make sure the 500 error page works
        // int i = 9 / 0;
        String blog = null;
        if (blog == null) {
            throw new NotFoundException("The chosen blog is not found.");
        }
        return "index";
    }
}
