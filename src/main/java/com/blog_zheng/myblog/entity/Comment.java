package com.blog_zheng.myblog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentID;
    private String name;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String email;
    // this stores the address of the avatar photo
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "bid", referencedColumnName = "blogid")
    @JsonIgnore
    private Blog blog;

    // When this comment is the parent comment
    @OneToMany(targetEntity = Comment.class, mappedBy = "parentComment")
    @ToString.Exclude
    private List<Comment> replies = new ArrayList<>();

    // When this comment is the child comment:
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_id", referencedColumnName = "commentid")
    private Comment parentComment;
}
