package com.blog_zheng.myblog.controller.admin;

import com.blog_zheng.myblog.entity.Blog;
import com.blog_zheng.myblog.entity.Category;
import com.blog_zheng.myblog.service.BlogService;
import com.blog_zheng.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private BlogService blogService;

    private CategoryService categoryService;

    @Autowired
    public BlogController(BlogService blogService, CategoryService categoryService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
    }

    @GetMapping("/blogs")
    public String blogs(Model model,
                        @PageableDefault(size = 4, sort = {"updateDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<Category> categoryList = categoryService.getCategoryList();
        Page<Blog> page = blogService.blogList(pageable, new Blog());
        model.addAttribute("page", page);
        model.addAttribute("categoryList", categoryList);
        return "admin/adminBlog";
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @PostMapping("/blogs/search")
    public String searchBlogs(Model model,
                              @PageableDefault(size = 4, sort = {"updateDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam("title") String title, @RequestParam("categoryID") Long categoryID) {
        // note that if the form data from the second parameter of the ajax.load() represents an Entity, we could use @RequestBody instead.
        // Spring will automatically map the data to the POJO by name for us.
        Blog blog = new Blog();
        blog.setTitle(title);
        Category category = new Category();
        category.setCategoryID(categoryID);
        blog.setCategory(category);
        Page<Blog> page = blogService.blogList(pageable, blog);
        model.addAttribute("page", page);
        return "admin/adminBlog :: result-table";
    }
}
