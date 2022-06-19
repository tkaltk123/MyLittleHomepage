package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long postId, CommentRequest postRequest);

    CommentResponse updateComment(CommentRequest postRequest);

    void deleteComment(Long commentId);

    CommentResponse getComment(Long commentId);

    Page<CommentResponse> getCommentList(Long postId, Integer page);

}
