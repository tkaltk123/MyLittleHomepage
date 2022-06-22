package com.yunseojin.MyLittleHomepage.comment.repository;

import com.yunseojin.MyLittleHomepage.comment.entity.CommentEntity;
import com.yunseojin.MyLittleHomepage.comment.entity.QCommentEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class DslCommentRepositoryImpl extends QuerydslRepositorySupport implements DslCommentRepository {

    private static final QCommentEntity c = QCommentEntity.commentEntity;

    public DslCommentRepositoryImpl() {
        super(CommentEntity.class);
    }

    @Override
    public Page<CommentEntity> getRootComments(PostEntity post, Pageable pageable) {

        var condition = c.post.eq(post).and(c.parent.isNull());

        var content = from(c)
                .select(c)
                .join(c.commentCount).fetchJoin()
                .where(condition)
                .orderBy(c.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var count = from(c)
                .select(c)
                .where(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }
}
