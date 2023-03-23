package com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.repository;

import com.yunseojin.MyLittleHomepage.v2.domain.contract.query.repository.ReadOnlyRepository;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedPostFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface QueriedPostFeedbackRepository extends ReadOnlyRepository<QueriedPostFeedback> {

}