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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogID;

    // The content is a large object, so MySQL will use LONGTEXT instead of varchar
    @Lob
    @Basic(fetch = FetchType.LAZY)
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

    // This will not be part of the database
    // format: 1,2,3,4,...
    @Transient
    private String tagIDs;

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "categoryid")
    private Category category;

    @ManyToMany(targetEntity = Tag.class, cascade = {CascadeType.PERSIST})
    // joinColumns represents the foreign key of the current object in the join table
    // inverseJoinColumns represents the foreign key of the other object in the join table
    @JoinTable(name = "tbl_blog_tag",
            joinColumns = {@JoinColumn(name = "b_blogid", referencedColumnName = "blogid")},
            inverseJoinColumns = {@JoinColumn(name = "t_tagid", referencedColumnName = "tagid")})
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class, mappedBy = "blog", cascade = {CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "userid")
    private User user;

}
