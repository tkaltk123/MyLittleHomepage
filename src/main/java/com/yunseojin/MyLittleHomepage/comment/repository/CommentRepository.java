package com.yunseojin.MyLittleHomepage.comment.repository;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>,
        DslCommentRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select cc.likeCount from CommentCountV2 cc " +
            "where cc.id = :commentId")
    Integer getLikeCount(@Param("commentId") Long commentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select cc.dislikeCount from CommentCountV2 cc " +
            "where cc.id = :commentId")
    Integer getDislikeCount(@Param("commentId") Long commentId);
}