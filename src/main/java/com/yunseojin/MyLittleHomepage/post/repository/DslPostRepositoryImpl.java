package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.hashtag.entity.QHashtagEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.entity.QPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class DslPostRepositoryImpl extends QuerydslRepositorySupport implements DslPostRepository {
    private static final QPostEntity p = QPostEntity.postEntity;
    private static final QHashtagEntity t = QHashtagEntity.hashtagEntity;

    public DslPostRepositoryImpl() {
        super(PostEntity.class);
    }

    @Override
    public Page<PostEntity> getPosts(BoardEntity board, Pageable pageable, PostSearch postSearch) {

        var contentQuery = from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(p.board.eq(board));

        var countQuery = from(p)
                .select(p)
                .where(p.board.eq(board));

        var keyword = postSearch.getKeyword();
        if (keyword != null && !keyword.isBlank()) {

            keyword = "%" + keyword + "%";
            switch (postSearch.getPostSearchType()) {

                case TITLE:
                    contentQuery.where(p.title.like(keyword));
                    countQuery.where(p.title.like(keyword));
                    break;

                case CONTENT:
                    contentQuery.where(p.content.like(keyword));
                    countQuery.where(p.content.like(keyword));
                    break;

                case WRITER:
                    contentQuery.where(p.writerName.like(keyword));
                    countQuery.where(p.writerName.like(keyword));
                    break;

                case TAG:
                    contentQuery = from(t)
                            .innerJoin(p).on(t.post.eq(p))
                            .select(p).distinct()
                            .join(p.postCount).fetchJoin()
                            .where(t.tag.like(keyword), p.board.eq(board));
                    countQuery = from(t)
                            .innerJoin(p).on(t.post.eq(p))
                            .select(p).distinct()
                            .where(t.tag.like(keyword), p.board.eq(board));
                    break;

                default:
                    break;
            }
        }

        var content = contentQuery
                .orderBy(p.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public PostEntity getPost(Long postId) throws BadRequestException {
        var post = from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(p.id.eq(postId))
                .fetchOne();

        if (post == null)
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_POST_EXCEPTION);

        return post;
    }
}
