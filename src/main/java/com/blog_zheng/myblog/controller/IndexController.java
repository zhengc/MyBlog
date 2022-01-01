package com.blog_zheng.myblog.controller;

import com.blog_zheng.myblog.entity.Blog;
import com.blog_zheng.myblog.entity.Category;
import com.blog_zheng.myblog.entity.Tag;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
                        Model model, @RequestParam(name = "search-query", defaultValue = "") String query) {
        Page<Blog> page;
        if (query != null) {
            page = blogService.blogList(pageable, query);
        } else {
            page = blogService.blogList(pageable, new Blog());
        }
        model.addAttribute("page", page);
        model.addAttribute("query", query);
        model.addAttribute("count", page.getTotalElements());
        model.addAttribute("categories", categoryService.getTopKCategory(K));
        model.addAttribute("tags", tagService.getTopKTags(K));
        model.addAttribute("latestBlogs", blogService.getLatestBlogs(K));
        return "index";
    }

    @GetMapping(value = {"/category", "/category/{id}"})
    public String filteredCategory(@PathVariable(name = "id", required = false) Long cid, Model model) {
        List<Category> categories = categoryService.getCategoryList();
        model.addAttribute("categories", categories);
        model.addAttribute("count", categories.size());
        if (cid != null) {
            Category chosenCategory = categoryService.getCategory(cid);
            model.addAttribute("blogs", chosenCategory.getBlogs());
        }
        return "category";
    }

    @GetMapping(value = {"/tag", "/tag/{id}"})
    public String tag(@PathVariable(name = "id", required = false) Long tid, Model model) {
        List<Tag> tags = tagService.getTagList();
        model.addAttribute("tags", tags);
        model.addAttribute("count", tags.size());
        if (tid != null) {
            Tag chosenTag = tagService.getTag(tid);
            model.addAttribute("blogs", chosenTag.getBlogs());
        }
        return "tag";
    }

}
