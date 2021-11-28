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
@Table(name = "Tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagID;
    @NotEmpty(message = "The name of the new tag should not be empty.")
    @Size(max = 30, message = "The length of the new tag name should not exceed 30 characters.")
    private String name;

    // passive side gives up the ownership using mappedBy, tags is the name of a field of Blog
    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private List<Blog> blogs = new ArrayList<>();
}
