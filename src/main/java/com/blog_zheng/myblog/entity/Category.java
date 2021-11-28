package com.blog_zheng.myblog.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    @NotEmpty(message = "Category name must not be empty.")
    @Size(max = 25, message = "The maximum length of a category name cannot exceed 25 characters.")
    private String name;

    // Here the blog is the owning side
    // the category is the inverse side
    // It is good practice to mark many-to-one side as the owning side, which is the blog
    // since obviously the blog is more important than category
    @OneToMany(targetEntity = Blog.class, mappedBy = "category")
    @ToString.Exclude
    private List<Blog> blogs = new ArrayList<>();

}
