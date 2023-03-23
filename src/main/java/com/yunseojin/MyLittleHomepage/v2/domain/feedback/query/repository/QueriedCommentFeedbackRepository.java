package com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.repository;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedCommentFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedCommentFeedbackRepository extends
        ReadOnlyRepository<QueriedCommentFeedback> {

    QueriedCommentFeedback getByWriterIdAndCommentId(Long writerId, Long commentId);

}