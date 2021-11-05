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

    @GetMapping("/categories/create")
    public String createCategory(Category category) {
        // include a Category in this method signature so the form will associate the form
        // attributes with this Category. Remember to use th:object in the respective html file.
        // For each field of the object, use th:field for each <input> html tag
        // -----------------------------------------------------------------------------------
        // Another way to associate the form with an object is to use Model and create a new
        // instance and add it to the Model. th:object and th:field should also be applied.
        return "admin/adminCategoryCreate";
    }

    @PostMapping("/categories/submit")
    public String postCategoryName(@Valid Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (categoryService.getCategoryByName(category.getName()) != null) {
            result.rejectValue("name", "NamingError", "This category already exists.");
        }
        if (result.hasErrors()) {
            return "admin/adminCategoryCreate";
        }
        Category savedValue = categoryService.saveCategory(category);
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

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        Category c = categoryService.getCategory(id);
        model.addAttribute("category", c);
        return "admin/adminCategoryCreate";
    }

    @PostMapping("/categories/edit/{id}")
    public String postCategoryName(@Valid Category category, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (categoryService.getCategoryByName(category.getName()) != null) {
            result.rejectValue("name", "NamingError", "This category already exists.");
        }
        if (result.hasErrors()) {
            return "admin/adminCategoryCreate";
        }
        Category savedValue = categoryService.updateCategory(id, category);
        if (savedValue == null) {
            redirectAttributes.addFlashAttribute("FailMessage", "Failed to update the category.");
        } else {
            redirectAttributes.addFlashAttribute("SuccessMessage", "Updated the category successfully!");
        }
        // Note: This must be re-direct not just merely return a web page because
        // after we update or modify the database, we need to go back to the server
        // and query once again to reflect the changes
        return "redirect:/admin/categories";
    }
}
