package com.yunseojin.MyLittleHomepage.comment.repository;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByPostAndParentIdIsNull(PostEntity post, Pageable pageable);

    default CommentEntity getComment(Long commentId) {
        var _comment = findById(commentId);
        if (_comment.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION);
        return _comment.get();
    }
}