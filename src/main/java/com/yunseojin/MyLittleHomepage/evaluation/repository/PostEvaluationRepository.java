package com.yunseojin.MyLittleHomepage.evaluation.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.PostEvaluationEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEvaluationRepository extends JpaRepository<PostEvaluationEntity, Long> {
    PostEvaluationEntity findByPostAndWriter(PostEntity post, MemberEntity writer);


    @Modifying
    @Query("update PostEvaluationEntity e " +
            "set e.isDeleted = 1" +
            "where e.post in " +
            "(select p from PostEntity p " +
            "where p.board = :board)")
    void deleteAllByBoard(@Param("board") BoardEntity board);

    @Modifying
    @Query("update PostEvaluationEntity e " +
            "set e.isDeleted = 1" +
            "where e.post = :post")
    void deleteAllByPost(@Param("post") PostEntity post);
}