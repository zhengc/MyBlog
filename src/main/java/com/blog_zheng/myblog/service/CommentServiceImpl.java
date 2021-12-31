package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.dao.CommentRepository;
import com.blog_zheng.myblog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.getById(id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentID = comment.getParentComment().getCommentID();
        if (parentCommentID == -1) {
            comment.setParentComment(null);
        } else {
            comment.setParentComment(getComment(parentCommentID));
        }
        comment.setDate(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments(Long blogID) {
        Sort sort = Sort.by(Sort.Direction.ASC, "date");
        return commentRepository.findByBlogBlogID(blogID, sort);
    }
}
