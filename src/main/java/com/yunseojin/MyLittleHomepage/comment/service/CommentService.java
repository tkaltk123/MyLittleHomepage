package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long memberId, Long postId, CommentRequest postRequest);

    CommentResponse updateComment(Long memberId, CommentRequest postRequest);

    void deleteComment(Long memberId, Long commentId);

    CommentResponse getComment(Long commentId);

    Page<CommentResponse> getCommentList(Long postId, Integer page);

}
