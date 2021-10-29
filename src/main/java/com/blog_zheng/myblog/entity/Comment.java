package com.blog_zheng.myblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long commentID;
    private String name;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String email;
    // this stores the address of the avatar photo
    private String avatar;

    @ManyToOne
    private Blog blog;

    // When this comment is the parent comment
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replies = new ArrayList<>();

    // When this comment is the child comment:
    @ManyToOne
    private Comment parentComment;

    public Comment() {
    }
}
