package com.yunseojin.MyLittleHomepage.comment.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.board.entity.QBoardEntity;
import com.yunseojin.MyLittleHomepage.board.repository.DslBoardRepository;
import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.entity.QCommentEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;

public class DslCommentRepositoryImpl extends QuerydslRepositorySupport implements DslCommentRepository {
    public DslCommentRepositoryImpl() {
        super(CommentEntity.class);
    }
    @Override
    public Page<CommentEntity> getRootComments(PostEntity post, Pageable pageable) {
        var c = QCommentEntity.commentEntity;
        //content 쿼리
        var content = from(c)
                .select(c)
                .join(c.commentCount)
                .fetchJoin()
                .where(c.post.eq(post), c.parent.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        //count 쿼리
        var count = from(c)
                .select(c)
                .where(c.post.eq(post), c.parent.isNull());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }

    @Override
    public CommentEntity getComment(Long commentId) {
        var c = QCommentEntity.commentEntity;
        var comment = from(c)
                .select(c)
                .join(c.commentCount)
                .fetchJoin()
                .where(c.id.eq(commentId))
                .fetchOne();
        if (comment == null)
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_COMMENT_EXCEPTION);
        return comment;
    }
}
