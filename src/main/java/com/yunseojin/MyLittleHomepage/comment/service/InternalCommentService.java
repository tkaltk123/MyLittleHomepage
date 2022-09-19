package com.yunseojin.MyLittleHomepage.comment.service;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluableService;

public interface InternalCommentService extends EvaluableService {

    CommentEntity getCommentById(Long commentId);
}
