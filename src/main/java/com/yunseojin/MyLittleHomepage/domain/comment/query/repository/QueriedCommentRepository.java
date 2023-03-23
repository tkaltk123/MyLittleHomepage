package com.yunseojin.MyLittleHomepage.domain.comment.query.repository;

import com.yunseojin.MyLittleHomepage.domain.contract.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.domain.comment.query.model.QueriedComment;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedCommentRepository extends ReadOnlyRepository<QueriedComment>,
        DslQueriedCommentRepository {

}