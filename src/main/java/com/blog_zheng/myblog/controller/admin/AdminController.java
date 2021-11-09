package com.blog_zheng.myblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/admin")
    public String goToManagementPage() {
        return "adminBlog";
    }

    @GetMapping("/edit")
    public String goToEditPage() {
        return "admin/publish";
    }
}
