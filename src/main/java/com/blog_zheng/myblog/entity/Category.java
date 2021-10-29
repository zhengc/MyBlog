package com.blog_zheng.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue
    private Long categoryID;
    private String name;

    // Here the blog is the owning side
    // the category is the inverse side
    // It is good practice to mark many-to-one side as the owning side, which is the blog
    // since obviously the blog is more important than category
    @OneToMany(mappedBy = "category")
    private List<Blog> blogs = new ArrayList<>();

    public Category() {
    }
}