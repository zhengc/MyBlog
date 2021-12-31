package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment getComment(Long id);

    Comment saveComment(Comment c);

    List<Comment> getAllComments(Long blogID);
}
