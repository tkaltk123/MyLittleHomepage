package com.yunseojin.MyLittleHomepage.evaluation.repository;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentEvaluationRepository extends JpaRepository<CommentEvaluationEntity, Long> {

    Optional<CommentEvaluationEntity> findByCommentAndWriter(CommentEntity comment, MemberEntity writer);
}