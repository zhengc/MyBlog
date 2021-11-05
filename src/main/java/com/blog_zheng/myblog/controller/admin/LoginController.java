package com.blog_zheng.myblog.controller.admin;

import com.blog_zheng.myblog.entity.User;
import com.blog_zheng.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // the default will go to the login page
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        User user = userService.loginCheck(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/adminIndex";
        }
        redirectAttributes.addFlashAttribute("message", "username does not match the password.");
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
