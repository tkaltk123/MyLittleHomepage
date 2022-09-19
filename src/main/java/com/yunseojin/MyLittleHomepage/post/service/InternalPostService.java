package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluableService;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;

public interface InternalPostService extends EvaluableService {

    void increaseViewCount(Long postId);

    void decreaseViewCount(Long postId);

    void increaseCommentCount(Long postId);

    void decreaseCommentCount(Long postId);

    PostEntity getPostById(Long postId);
}
