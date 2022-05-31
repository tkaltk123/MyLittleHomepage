package com.yunseojin.MyLittleHomepage.comment.repository;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslCommentRepository {
    Page<CommentEntity> getRootComments(PostEntity post, Pageable pageable);

    CommentEntity getComment(Long commentId);
}
