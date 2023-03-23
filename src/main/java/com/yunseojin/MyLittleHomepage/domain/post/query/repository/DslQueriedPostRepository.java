package com.yunseojin.MyLittleHomepage.domain.post.query.repository;

import com.yunseojin.MyLittleHomepage.domain.post.query.model.QueriedPost;
import com.yunseojin.MyLittleHomepage.domain.post.vo.PostOrderType;
import com.yunseojin.MyLittleHomepage.domain.post.vo.PostSearchVo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslQueriedPostRepository {

    Page<QueriedPost> getPosts(PostSearchVo postSearchVo, Pageable pageable);

    Page<QueriedPost> getPostsWithCursor(PostSearchVo postSearchVo, boolean isAsc, Long cursor,
            Pageable pageable);

    List<QueriedPost> getOrderedPostsLimit(Long boardId, PostOrderType postOrderType, int limit);
}
