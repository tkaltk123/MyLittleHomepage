package com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity.QueriedComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedCommentRepository extends JpaRepository<QueriedComment, Long>,
        DslQueriedCommentRepository {

}