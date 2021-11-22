package com.blog_zheng.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long blogID;
    private String content;
    private Integer viewCount;
    private Boolean openComment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;
    private String Authentication;
    private String author;
    private String photo;
    private String title;
    // whether publish the blog right after editing (true) or save it for later use (false)
    private Boolean publish;

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private User user;

}
