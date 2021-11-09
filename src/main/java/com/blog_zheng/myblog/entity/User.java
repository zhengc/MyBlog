package com.blog_zheng.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    private Long userID;
    private String name;
    private String password;
    private Boolean isAdmin;
    private String avatar;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();
}
