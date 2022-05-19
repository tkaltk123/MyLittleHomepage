package com.yunseojin.MyLittleHomepage.evaluation.repository;

import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentEvaluationRepository extends JpaRepository<CommentEvaluationEntity, Long> {
    CommentEvaluationEntity findByCommentIdAndWriterId(Long commentId, Long writerId);
}