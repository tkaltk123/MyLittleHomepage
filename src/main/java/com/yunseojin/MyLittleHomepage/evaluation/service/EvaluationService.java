package com.yunseojin.MyLittleHomepage.evaluation.service;

import com.yunseojin.MyLittleHomepage.etc.enums.EvaluationType;

public interface EvaluationService {

    EvaluationType likePost(Long postId);

    EvaluationType likeComment(Long commentId);

    EvaluationType dislikePost(Long postId);

    EvaluationType dislikeComment(Long commentId);
}
