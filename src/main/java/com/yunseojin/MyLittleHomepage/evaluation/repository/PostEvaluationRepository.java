package com.yunseojin.MyLittleHomepage.evaluation.repository;

import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEvaluationRepository extends JpaRepository<PostEvaluationEntity, Long> {

    Optional<PostEvaluationEntity> findByPostAndWriter(PostEntity post, Member writer);
}