package com.yunseojin.MyLittleHomepage.evaluation.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.evaluation.entity.CommentEvaluationEntity;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentEvaluationRepository extends JpaRepository<CommentEvaluationEntity, Long> {
    CommentEvaluationEntity findByCommentAndWriter(CommentEntity comment, MemberEntity writer);

    @Modifying
    @Query(" update CommentEvaluationEntity e " +
            "set e.isDeleted = 1" +
            "where e.comment in " +
            "(select c from CommentEntity c " +
            "where c.post in " +
            "(select p from PostEntity p " +
            "where p.board = :board))")
    void deleteAllByBoard(@Param("board") BoardEntity board);

    @Modifying
    @Query("update CommentEvaluationEntity e " +
            "set e.isDeleted = 1" +
            "where e.comment in " +
            "(select c from CommentEntity c " +
            "where c.post = :post)")
    void deleteAllByPost(@Param("post") PostEntity post);

    @Modifying
    @Query("update CommentEvaluationEntity e " +
            "set e.isDeleted = 1" +
            "where e.comment in " +
            "(select c from CommentEntity c " +
            "where c.parent = :parent)")
    void deleteAllByParent(@Param("parent") CommentEntity parent);

    @Modifying
    @Query("update CommentEvaluationEntity e " +
            "set e.isDeleted = 1" +
            "where e.comment = :comment")
    void deleteAllByComment(@Param("comment") CommentEntity comment);
}