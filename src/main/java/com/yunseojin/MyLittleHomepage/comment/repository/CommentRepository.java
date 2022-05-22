package com.yunseojin.MyLittleHomepage.comment.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByPostAndParentIdIsNull(PostEntity post, Pageable pageable);

    @Modifying
    @Query("update CommentEntity c " +
            "set c.isDeleted = 1" +
            "where c.post in " +
            "(select p from PostEntity p " +
            "where p.board = :board)")
    void deleteAllByBoard(@Param("board") BoardEntity board);

    @Modifying
    @Query("update CommentEntity c " +
            "set c.isDeleted = 1" +
            "where c.post = :post")
    void deleteAllByPost(@Param("post") PostEntity post);

    @Modifying
    @Query("update CommentEntity c " +
            "set c.isDeleted = 1" +
            "where c.parent = :parent")
    void deleteAllByParent(@Param("parent") CommentEntity parent);

    default CommentEntity getComment(Long commentId) {
        var _comment = findById(commentId);
        if (_comment.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION);
        return _comment.get();
    }
}