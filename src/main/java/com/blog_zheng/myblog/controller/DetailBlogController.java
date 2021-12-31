package com.blog_zheng.myblog.controller;

import com.blog_zheng.myblog.entity.Blog;
import com.blog_zheng.myblog.entity.Comment;
import com.blog_zheng.myblog.service.BlogService;
import com.blog_zheng.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DetailBlogController {

    private BlogService blogService;

    private CommentService commentService;

    private final String avatar = "/images/visitor.png";

    @Autowired
    public DetailBlogController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @GetMapping("/blogPage/{id}")
    public String detailPage(@PathVariable("id") Long id, Model model) {
        Blog b = blogService.getBlog(id);
        Integer oldViewCount = b.getViewCount();
        Blog blog = new Blog();
        blog.setViewCount(oldViewCount + 1);
        Blog result = blogService.updateBlog(id, blog);
        model.addAttribute("blog", result);
        model.addAttribute("comments", commentService.getAllComments(result.getBlogID()));
        return "blogPage";
    }

    @PostMapping("/submit_cmt")
    public String commentSubmit(Comment comment, Model model) {
        Long bid = comment.getBlog().getBlogID();
        comment.setAvatar(avatar);
        // note that comment will maintain the relationship, so there is no need to add the comment to the comment list of the blog
        comment.setBlog(blogService.getBlog(bid));
        commentService.saveComment(comment);
        model.addAttribute("comments", commentService.getAllComments(bid));
        return "blogPage :: commentList";
    }
}
