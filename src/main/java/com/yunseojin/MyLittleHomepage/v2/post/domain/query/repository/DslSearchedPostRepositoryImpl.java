package com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QQueriedPost;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.vo.PostOrderType;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.vo.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.vo.PostSearchVo;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class DslSearchedPostRepositoryImpl extends QuerydslRepositorySupport implements
        DslSearchedPostRepository {

    private static final QQueriedPost p = QQueriedPost.queriedPost;

    public DslSearchedPostRepositoryImpl() {
        super(QueriedPost.class);
    }


    @Override
    public Page<QueriedPost> getPosts(PostSearchVo postSearchVo, Pageable pageable) {

        BooleanExpression condition = getCondition(postSearchVo);
        var content = getPostList(condition, false, pageable);
        var countQuery = from(p).select(p).where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression getCondition(PostSearchVo postSearchVo) {
        var equalBoardId = equalBoardId(postSearchVo.getBoardId());
        var likeKeyword = likeKeyword(postSearchVo.getPostSearchType(), postSearchVo.getKeyword());
        return Expressions.allOf(equalBoardId, likeKeyword);
    }

    private BooleanExpression equalBoardId(Long boardId) {
        if (boardId != null) {
            return p.board.id.eq(boardId);
        }
        return null;
    }

    private BooleanExpression likeKeyword(PostSearchType postSearchType, String keyword) {
        if (Strings.isNotBlank(keyword)) {
            var column = descBy(postSearchType);
            if (Objects.nonNull(column)) {
                return column.like("%" + keyword + "%");
            }
        }
        return null;
    }

    private StringPath descBy(PostSearchType type) {
        switch (type) {
            case TITLE:
                return p.title;

            case CONTENT:
                return p.content;

            case WRITER:
                return p.writerName;
        }
        return null;
    }

    private List<QueriedPost> getPostList(BooleanExpression condition, boolean isAsc,
            Pageable pageable) {

        return from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(condition)
                .orderBy(isAsc ? p.id.asc() : p.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Page<QueriedPost> getPostsWithCursor(PostSearchVo postSearchVo, boolean isAsc,
            Long cursor, Pageable pageable) {

        BooleanExpression condition = Expressions.allOf(getCondition(postSearchVo),
                customCursor(isAsc, cursor));
        var content = getPostList(condition, isAsc, pageable);
        var countQuery = from(p).select(p).where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression customCursor(boolean isAsc, Long postId) {

        if (postId == null) {
            return null;
        }

        if (isAsc) {
            return p.id.gt(postId);
        }

        return p.id.lt(postId);

    }

    @Override
    public List<QueriedPost> getOrderedPostsLimit(Long boardId, PostOrderType postOrderType,
            int limit) {

        return from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(p.board.id.eq(boardId), createdIn7Days())
                .orderBy(descBy(postOrderType), p.id.desc())
                .limit(limit)
                .fetch();
    }


    private static BooleanExpression createdIn7Days() {
        return p.createdAt.after(Timestamp.valueOf(LocalDateTime.now().minusDays(7)));
    }

    private OrderSpecifier<Integer> descBy(PostOrderType postOrderType) {
        switch (postOrderType) {

            case COMMENT:
                return p.postCount.commentCount.desc();

            case VIEW:
                return p.postCount.viewCount.desc();

            case LIKE:
            default:
                return p.postCount.likeCount.desc();
        }
    }
}
