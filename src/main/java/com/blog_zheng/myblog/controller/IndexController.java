package com.blog_zheng.myblog.controller;

import com.blog_zheng.myblog.customExceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable Integer id, @PathVariable String name) {
        // This line is to make sure the 500 error page works
        // int i = 9 / 0;
        //String blog = null;
        //if (blog == null) {
        //    throw new NotFoundException("The chosen blog is not found.");
        //}
        System.out.println("------index------");
        return "index";
    }
}
