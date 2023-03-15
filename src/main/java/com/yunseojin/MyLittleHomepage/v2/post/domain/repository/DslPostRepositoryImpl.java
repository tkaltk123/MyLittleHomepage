package com.yunseojin.MyLittleHomepage.v2.post.domain.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.post.dto.FullPostSearch;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;
import com.yunseojin.MyLittleHomepage.v2.post.domain.model.QPost;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class DslPostRepositoryImpl extends QuerydslRepositorySupport implements DslPostRepository {

    private static final QPost p = QPost.post;

    public DslPostRepositoryImpl() {
        super(PostEntity.class);
    }


    @Override
    public Page<Post> getPosts(Long boardId, Pageable pageable, PostSearch postSearch) {

        var condition = createSearchCondition(boardId, postSearch);

        var content = getPostList(condition, pageable);
        var countQuery = from(p).select(p).where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<Post> getPostsWithCursor(Long lastPostId, Pageable pageable,
            FullPostSearch postSearch) {

        var condition = createSearchCondition(postSearch.getBoardId(), postSearch);

        var content = from(p).select(p)
                .join(p.postCount).fetchJoin()
                .where(condition, customCursor(lastPostId, postSearch.getIsAsc()))
                .orderBy(Boolean.TRUE.equals(postSearch.getIsAsc()) ? p.id.asc() : p.id.desc())
                .limit(pageable.getPageSize())
                .fetch();

        var countQuery = from(p).select(p).where(condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }

    @Override
    public List<Post> getPostsOrderedBy(Long boardId, int postCount, PostOrderType postOrderType) {

        var query = from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(p.boardId.eq(boardId),
                        p.createdAt.after(Timestamp.valueOf(LocalDateTime.now().minusDays(7))));

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

    private BooleanExpression createSearchCondition(Long boardId, PostSearch postSearch) {

        BooleanExpression condition = null;

        if (boardId != null) {
            condition = p.boardId.eq(boardId);
        }

        var keyword = postSearch.getKeyword();

        if (keyword != null && !keyword.isBlank()) {

            keyword = "%" + keyword + "%";
            var searchCond = includeKeyword(keyword, postSearch.getPostSearchType());

            if (condition == null) {
                return searchCond;
            }

            condition = condition.and(searchCond);
        }

        return condition;
    }

    private List<Post> getPostList(BooleanExpression condition, Pageable pageable) {

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

        if (postId == null) {
            return null;
        }

        if (isAsc) {
            return p.id.gt(postId);
        }
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

            default:
                return null;
        }
    }
}
