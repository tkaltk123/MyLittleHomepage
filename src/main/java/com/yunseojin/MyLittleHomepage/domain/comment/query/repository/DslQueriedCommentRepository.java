package com.yunseojin.MyLittleHomepage.domain.comment.query.repository;

import com.yunseojin.MyLittleHomepage.domain.comment.query.model.QueriedComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslQueriedCommentRepository {

    Page<QueriedComment> getRootComments(Long postId, Pageable pageable);
}
