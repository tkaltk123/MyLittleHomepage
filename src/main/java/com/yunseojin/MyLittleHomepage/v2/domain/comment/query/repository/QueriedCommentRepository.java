package com.yunseojin.MyLittleHomepage.v2.domain.comment.query.repository;

import com.yunseojin.MyLittleHomepage.v2.domain.comment.query.model.QueriedComment;
import com.yunseojin.MyLittleHomepage.v2.domain.contract.query.repository.ReadOnlyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedCommentRepository extends ReadOnlyRepository<QueriedComment>,
        DslQueriedCommentRepository {

}