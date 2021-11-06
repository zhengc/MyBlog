package com.blog_zheng.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue
    private Long tagID;
    @NotEmpty(message = "The name of the new tag should not be empty.")
    @Size(max = 30, message = "The length of the new tag name should not exceed 30 characters.")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();
}
