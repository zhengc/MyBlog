package com.blog_zheng.myblog.controller;

import com.blog_zheng.myblog.customExceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/blogPage")
    public String detailedPage() {
        return "blogPage";
    }

    @GetMapping("/category")
    public String category() {
        return "category";
    }

    @GetMapping("/tag")
    public String tag() {
        return "tag";
    }

}
