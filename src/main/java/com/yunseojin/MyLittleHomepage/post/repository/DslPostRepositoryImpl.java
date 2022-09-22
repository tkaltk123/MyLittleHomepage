package com.yunseojin.MyLittleHomepage.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.hashtag.entity.QHashtagEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.entity.QPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class DslPostRepositoryImpl extends QuerydslRepositorySupport implements DslPostRepository {

    private static final QPostEntity p = QPostEntity.postEntity;
    private static final QHashtagEntity t = QHashtagEntity.hashtagEntity;

    public DslPostRepositoryImpl() {
        super(PostEntity.class);
    }


    @Override
    public Page<PostEntity> getPosts(BoardEntity board, Pageable pageable, PostSearch postSearch) {

        var condition = createSearchCondition(board, postSearch);

        if (postSearch.getPostSearchType() == PostSearchType.TAG)
            return getPostsByTag(condition, pageable);

        var content = getPostList(condition, pageable);
        var countQuery = from(p).select(p).where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<PostEntity> getPostsWithCursor(Long lastPostId, BoardEntity board, Pageable pageable, PostSearch postSearch, boolean isAsc) {

        var condition = createSearchCondition(board, postSearch);

        var content = from(p).select(p)
                .join(p.postCount).fetchJoin()
                .where(condition, customCursor(lastPostId, isAsc))
                .orderBy(isAsc ? p.id.asc() : p.id.desc())
                .limit(pageable.getPageSize())
                .fetch();

        var countQuery = from(p).select(p).where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }

    @Override
    public List<PostEntity> getPostsOrderedBy(BoardEntity board, int postCount, PostOrderType postOrderType) {


        var query = from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(p.board.eq(board), p.createdAt.after(Timestamp.valueOf(LocalDateTime.now().minusDays(7))));

        switch (postOrderType) {

            case LIKE:
                query.orderBy(p.postCount.likeCount.desc(), p.id.desc());
                break;

            case COMMENT:
                query.orderBy(p.postCount.commentCount.desc(), p.id.desc());
                break;

            case VIEW:
                query.orderBy(p.postCount.viewCount.desc(), p.id.desc());
                break;

            default:
                break;
        }

        return query.limit(postCount).fetch();
    }

    private BooleanExpression createSearchCondition(BoardEntity board, PostSearch postSearch) {

        BooleanExpression condition = null;

        if (board != null)
            condition = p.board.eq(board);

        var keyword = postSearch.getKeyword();

        if (keyword != null && !keyword.isBlank()) {

            keyword = "%" + keyword + "%";
            var searchCond = includeKeyword(keyword, postSearch.getPostSearchType());

            if (condition == null)
                return searchCond;

            condition = condition.and(searchCond);
        }

        return condition;
    }

    private List<PostEntity> getPostList(BooleanExpression condition, Pageable pageable) {

        return from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(condition)
                .orderBy(p.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression customCursor(Long postId, boolean isAsc) {

        if (postId == null)
            return null;

        if (isAsc)
            return p.id.gt(postId);
        return p.id.lt(postId);

    }

    private BooleanExpression includeKeyword(String keyword, PostSearchType postSearchType) {

        switch (postSearchType) {

            case TITLE:
                return p.title.like(keyword);

            case CONTENT:
                return p.content.like(keyword);

            case WRITER:
                return p.writerName.like(keyword);

            case TAG:
                return t.tag.like(keyword);

            default:
                return null;
        }
    }

    private Page<PostEntity> getPostsByTag(BooleanExpression condition, Pageable pageable) {

        var content = from(t)
                .innerJoin(p).on(t.post.eq(p))
                .select(p).distinct()
                .join(p.postCount).fetchJoin()
                .where(condition)
                .orderBy(p.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var countQuery = from(t)
                .innerJoin(p).on(t.post.eq(p))
                .select(p).distinct()
                .where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}
