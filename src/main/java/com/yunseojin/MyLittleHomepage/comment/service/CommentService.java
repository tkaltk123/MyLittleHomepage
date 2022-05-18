package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(Long postId, CommentRequest postRequest);

    CommentResponse updateComment(Long commentId, CommentRequest postRequest);

    void deleteComment(Long commentId);

    List<CommentResponse> getCommentList(Long postId, Integer page);

}
