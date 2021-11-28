package com.blog_zheng.myblog.controller.admin;

import com.blog_zheng.myblog.entity.Tag;
import com.blog_zheng.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 4, sort = {"tagID"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<Tag> page = tagService.tagList(pageable);
        model.addAttribute("page", page);
        return "admin/adminTag";
    }

    @GetMapping("/tags/create")
    public String createTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/adminTagCreate";
    }

    @PostMapping("/tags/submit")
    public String postTagName(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "NamingError", "The tag name already exists!");
            return "admin/adminTagCreate";
        }
        if (result.hasErrors()) {
            return "admin/adminTagCreate";
        }
        Tag savedTag = tagService.saveTag(tag);
        if (savedTag == null) {
            redirectAttributes.addFlashAttribute("FailMessage", "Failed to create the new tag.");
        } else {
            redirectAttributes.addFlashAttribute("SuccessMessage", "Created the tag successfully!");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/edit/{id}")
    public String editTag(@PathVariable("id") Long id, Model model) {
        Tag t = tagService.getTag(id);
        model.addAttribute("tag", t);
        return "admin/adminTagCreate";
    }

    @PostMapping("tags/edit/{id}")
    public String updateTag(@PathVariable("id") Long id, @Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {
        // check if the tag name already exists
        Tag t = tagService.getTagByName(tag.getName());
        if (t != null) {
            result.rejectValue("name", "NamingError", "The tag name already exists!");
            return "admin/adminTagCreate";
        }
        if (result.hasErrors()) {
            return "admin/adminTagCreate";
        }
        Tag savedTag = tagService.updateTag(id, tag);
        if (savedTag == null) {
            redirectAttributes.addFlashAttribute("FailMessage", "Failed to update the tag.");
        } else {
            redirectAttributes.addFlashAttribute("SuccessMessage", "Updated the tag successfully!");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/delete/{id}")
    public String deleteTag(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            tagService.deleteTag(id);
            redirectAttributes.addFlashAttribute("s_msg", "Deleted the tag successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("f_msg", "Failed to delete the tag. Maybe it has been bounded to some blog. Consider editing instead of deleting.");
        }
        return "redirect:/admin/tags";
    }
}
