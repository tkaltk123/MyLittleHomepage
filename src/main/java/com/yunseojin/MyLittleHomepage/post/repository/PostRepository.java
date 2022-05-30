package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query(value = "select distinct p from PostEntity p " +
            "join fetch p.postCount " +
            "where p.board = :board",
            countQuery = "select count(p) from PostEntity p " +
                    "where p.board = :board")
    Page<PostEntity> findByBoard(@Param("board") BoardEntity board, Pageable pageable);

    @Query("select distinct p from PostEntity p " +
            "join fetch p.postCount " +
            "join fetch p.board b " +
            "join fetch b.boardCount " +
            "where p.id = :postId")
    PostEntity findByIdWithJoin(@Param("postId") Long postId);

    default PostEntity getPost(Long postId) {
        var post = findByIdWithJoin(postId);
        if (post == null)
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_POST_EXCEPTION);
        return post;
    }
}