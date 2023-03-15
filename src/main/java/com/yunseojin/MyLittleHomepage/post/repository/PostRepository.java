package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, DslPostRepository {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc.viewCount from PostCountV2 pc " +
            "where pc.id = :postId")
    Integer getViewCount(@Param("postId") Long postId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc.commentCount from PostCountV2 pc " +
            "where pc.id = :postId")
    Integer getCommentCount(@Param("postId") Long postId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc.likeCount from PostCountV2 pc " +
            "where pc.id = :postId")
    Integer getLikeCount(@Param("postId") Long postId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select pc.dislikeCount from PostCountV2 pc " +
            "where pc.id = :postId")
    Integer getDislikeCount(@Param("postId") Long postId);
}