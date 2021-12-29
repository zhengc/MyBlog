package com.blog_zheng.myblog.controller;

import com.blog_zheng.myblog.entity.Blog;
import com.blog_zheng.myblog.service.BlogService;
import com.blog_zheng.myblog.service.CategoryService;
import com.blog_zheng.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private BlogService blogService;

    private CategoryService categoryService;

    private TagService tagService;

    private final int K = 4;

    @Autowired
    public IndexController(BlogService blogService, CategoryService categoryService, TagService tagService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String index(@PageableDefault(size = 4, sort = {"updateDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        Page<Blog> page = blogService.blogList(pageable, new Blog());
        model.addAttribute("page", page);
        long count = blogService.numBlogs();
        model.addAttribute("count", count);
        model.addAttribute("categories", categoryService.getTopKCategory(K));
        model.addAttribute("tags", tagService.getTopKTags(K));
        model.addAttribute("latestBlogs", blogService.getLatestBlogs(K));
        return "index";
    }

    @GetMapping("/blogPage/{id}")
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
