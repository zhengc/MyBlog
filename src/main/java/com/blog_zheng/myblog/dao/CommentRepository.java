package com.blog_zheng.myblog.dao;

import com.blog_zheng.myblog.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBlogBlogID(Long blogID, Sort sort);
}
