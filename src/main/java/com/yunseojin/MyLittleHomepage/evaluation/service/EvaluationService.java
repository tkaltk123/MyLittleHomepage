package com.yunseojin.MyLittleHomepage.evaluation.service;

public interface EvaluationService {
    String likePost(Long postId);

    String likeComment(Long commentId);

    String dislikePost(Long postId);

    String dislikeComment(Long commentId);
}
