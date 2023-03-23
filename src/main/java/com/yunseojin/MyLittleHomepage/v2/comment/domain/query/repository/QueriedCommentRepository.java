package com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity.QueriedComment;
import com.yunseojin.MyLittleHomepage.v2.contract.domain.query.repository.ReadOnlyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedCommentRepository extends ReadOnlyRepository<QueriedComment>,
        DslQueriedCommentRepository {

}