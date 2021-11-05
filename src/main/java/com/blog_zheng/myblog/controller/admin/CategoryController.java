package com.blog_zheng.myblog.controller.admin;

import com.blog_zheng.myblog.entity.Category;
import com.blog_zheng.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    // sort the pageable content with id in descending order
    public String categories(@PageableDefault(size = 3, sort = {"categoryID"}, direction = Sort.Direction.DESC) Pageable pageable,
                             Model model) {
        Page<Category> page = categoryService.categoryList(pageable);
        // send the page object to the front end with the help of model
        model.addAttribute("page", page);
        return "admin/adminCategory";
    }

    // TODO: this method is problematic: cannot construct a category with empty name.
    @GetMapping("/categories/create")
    public String createCategory(Model model) {
        // send an empty category to the create page for backend validation
        model.addAttribute("currCategory", new Category(""));
        return "admin/adminCategoryCreate";
    }

    @PostMapping("/categories/submit")
    public String postCategoryName(@RequestParam("categoryName") String name, RedirectAttributes redirectAttributes) {
        Category c = new Category(name);
        Category savedValue = categoryService.saveCategory(c);
        if (savedValue == null) {
            redirectAttributes.addFlashAttribute("FailMessage", "Failed to add the new category.");
        } else {
            redirectAttributes.addFlashAttribute("SuccessMessage", "Added the new category successfully!");
        }
        // Note: This must be re-direct not just merely return a web page because
        // after we update or modify the database, we need to go back to the server
        // and query once again to reflect the changes
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("s_msg", "Deleted the category successfully!");
        return "redirect:/admin/categories";
    }
}
