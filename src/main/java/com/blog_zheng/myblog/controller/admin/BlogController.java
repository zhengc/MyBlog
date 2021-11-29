package com.blog_zheng.myblog.controller.admin;

import com.blog_zheng.myblog.entity.Blog;
import com.blog_zheng.myblog.entity.Category;
import com.blog_zheng.myblog.entity.Tag;
import com.blog_zheng.myblog.entity.User;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private BlogService blogService;

    private CategoryService categoryService;

    private TagService tagService;

    @Autowired
    public BlogController(BlogService blogService, CategoryService categoryService, TagService tagService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @GetMapping("/blogs")
    public String blogs(Model model,
                        @PageableDefault(size = 4, sort = {"updateDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<Category> categoryList = categoryService.getCategoryList();
        Blog blog = new Blog();
        blog.setCategory(new Category());
        Page<Blog> page = blogService.blogList(pageable, blog);
        model.addAttribute("page", page);
        model.addAttribute("categoryList", categoryList);
        return "admin/adminBlog";
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @PostMapping("/blogs/search")
    public String searchBlogs(Model model,
                              @PageableDefault(size = 4, sort = {"updateDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                              HttpServletRequest request) {
        // method 1: to get ajax data -> use HttpServletRequest.getParameter
        String title = request.getParameter("title");
        Blog blog = new Blog();
        blog.setTitle(title);
        Category category = new Category();
        String categoryID = request.getParameter("categoryID");
        if (categoryID != null && !categoryID.isEmpty()) {
            Long id = new Long(categoryID);
            category.setCategoryID(id);
        }
        blog.setCategory(category);
        // method 2: Declare a Blog object and a Category Object as part of the signature of this controller method
        //           (because Blog class does not have a field named categoryID),
        //           spring will encapsulate them for us, and pass the Blog object directly to .blogList().
        //           This is a lot simpler, but I used method 1 any way.
        Page<Blog> page = blogService.blogList(pageable, blog);
        model.addAttribute("page", page);
        return "admin/adminBlog :: result-table";
    }

    @GetMapping("/blogs/create")
    public String createBlog(Model model) {
        Blog blog = new Blog();
        blog.setCategory(new Category());
        model.addAttribute("blog", blog);
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("tags", tagService.getTagList());
        return "admin/publish";
    }

    @PostMapping("/blogs/publish")
    // get the object populated by the form
    public String publishBlog(@ModelAttribute Blog blog, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        // set the user of the blog
        blog.setUser(user);

        // set the correct category object
        blog.setCategory(categoryService.getCategory(blog.getCategory().getCategoryID()));
        // set the tags
        blog.setTags(tagService.getTagList(blog.getTagIDs()));

        // save or update this blog to the database
        Blog b = blogService.saveBlog(blog);

        if (b == null) {
            redirectAttributes.addFlashAttribute("fmsg", "Sorry, cannot create or update the blog. Please try again.");
        } else {
            redirectAttributes.addFlashAttribute("smsg", "Successfully created or updated a blog!");
        }

        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/edit/{id}")
    public String editBlog(@PathVariable("id") Long id, Model model) {
        Blog b = blogService.getBlog(id);
        List<Tag> tags = b.getTags();
        String ids = stringifyTags(tags);
        b.setTagIDs(ids);
        model.addAttribute("blog", b);
        model.addAttribute("categories", categoryService.getCategoryList());
        model.addAttribute("tags", tagService.getTagList());
        return "/admin/publish";
    }

    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("smsg", "Successfully deleted the blog!");
        return "redirect:/admin/blogs";
    }

    /**
     * This function will stringify the list of tags into a string of tagIDs separated by comma.
     * This is necessary because the field tagIDs is not part of the database, so we need to initialize
     * the value when we need it.
     */
    private String stringifyTags(List<Tag> list) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size() - 1; i++) {
            buffer.append(list.get(i).getTagID());
            buffer.append(",");
        }
        buffer.append(list.get(list.size() - 1).getTagID());
        return buffer.toString();
    }
}
