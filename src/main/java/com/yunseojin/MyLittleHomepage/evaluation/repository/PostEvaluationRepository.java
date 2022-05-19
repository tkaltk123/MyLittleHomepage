package com.yunseojin.MyLittleHomepage.evaluation.repository;

import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEvaluationRepository extends JpaRepository<PostEvaluationEntity, Long> {
    PostEvaluationEntity findByPostIdAndWriterId(Long postId, Long writerId);
}