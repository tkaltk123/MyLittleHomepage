package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import com.yunseojin.MyLittleHomepage.post.entity.QPostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class DslPostRepositoryImpl extends QuerydslRepositorySupport implements DslPostRepository {
    private static final QPostEntity p = QPostEntity.postEntity;

    public DslPostRepositoryImpl() {
        super(PostEntity.class);
    }

    @Override
    public Page<PostEntity> getPosts(BoardEntity board, Pageable pageable) {
        var content = from(p)
                .select(p)
                .join(p.postCount).fetchJoin()
                .where(p.board.eq(board))
                .orderBy(p.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var count = from(p)
                .select(p)
                .where(p.board.eq(board));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
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
