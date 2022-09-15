package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;

public interface EvaluationService {

    EvaluationType likePost(Long memberId, Long postId);

    EvaluationType likeComment(Long memberId, Long commentId);

    EvaluationType dislikePost(Long memberId, Long postId);

    EvaluationType dislikeComment(Long memberId, Long commentId);
}
