package com.yunseojin.MyLittleHomepage.v2.comment.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity.QQueriedComment;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.query.entity.QueriedComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class DslQueriedCommentRepositoryImpl extends QuerydslRepositorySupport implements
        DslQueriedCommentRepository {

    private static final QQueriedComment c = QQueriedComment.queriedComment;

    public DslQueriedCommentRepositoryImpl() {
        super(QQueriedComment.class);
    }

    @Override
    public Page<QueriedComment> getRootComments(Long postId, Pageable pageable) {

        var condition = c.postId.eq(postId).and(c.parentId.isNull());

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
