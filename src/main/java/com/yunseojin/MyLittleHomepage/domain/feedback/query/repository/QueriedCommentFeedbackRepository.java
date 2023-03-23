package com.yunseojin.MyLittleHomepage.domain.feedback.query.repository;

import com.yunseojin.MyLittleHomepage.domain.contract.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.domain.feedback.query.model.QueriedCommentFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedCommentFeedbackRepository extends
        ReadOnlyRepository<QueriedCommentFeedback> {

}