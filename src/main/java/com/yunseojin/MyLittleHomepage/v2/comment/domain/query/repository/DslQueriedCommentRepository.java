package com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity.QueriedComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslQueriedCommentRepository {

    Page<QueriedComment> getRootComments(Long postId, Pageable pageable);
}
