package com.yunseojin.MyLittleHomepage.evaluation.repository;

import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostEvaluationRepository extends JpaRepository<PostEvaluationEntity, Long> {

    Optional<PostEvaluationEntity> findByPostAndWriter(PostEntity post, MemberEntity writer);
}