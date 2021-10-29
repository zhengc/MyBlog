package com.blog_zheng.myblog.controller.admin;

import com.blog_zheng.myblog.entity.User;
import com.blog_zheng.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

//TODO: Buggy login, did not redirect to the front page correctly.
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
    public String login(@RequestParam String name,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        User user = userService.loginCheck(name, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "index";
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