package com.blog_zheng.myblog.service;

import com.blog_zheng.myblog.dao.CommentRepository;
import com.blog_zheng.myblog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Comment> comments = commentRepository.findByBlogBlogID(blogID, sort);
        return recurseReply(comments);
    }

    // Get the replies of each comment
    // All recursive replies belong to the current topmost comment, therefore,
    // instead of having multiple layers for each reply, we have only two layers:
    // comment section structure is like this:
    // --- topmost comment(0)
    // --- --- reply1-0
    // --- --- reply2-1
    // --- --- reply1-2
    // --- --- reply3-0
    //
    // Return a list of comments where each comment contains all its replies
    private List<Comment> recurseReply(List<Comment> comments) {
        List<Comment> result = new ArrayList<>();
        for (Comment comment : comments) {
            // we first find the topmost comment
            if (comment.getParentComment() == null) {
                List<Comment> replies = comment.getReplies();
                List<Comment> combinedReplies = new ArrayList<>();

                // store all recursive sub-replies to the combinedReplies list
                for (Comment reply : replies) {
                    helper(reply, combinedReplies);
                }
                comment.setReplies(combinedReplies);
                result.add(comment);
            }
        }
        return result;
    }

    private void helper(Comment reply, List<Comment> list) {
        if (!list.contains(reply)) {
            list.add(reply);
        }
        for (Comment subReply : reply.getReplies()) {
            helper(subReply, list);
        }
    }
}
