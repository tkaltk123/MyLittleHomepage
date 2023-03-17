package com.yunseojin.MyLittleHomepage.v2.post.domain.query.repository;

import com.yunseojin.MyLittleHomepage.v2.post.domain.query.entity.QueriedPost;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.vo.PostOrderType;
import com.yunseojin.MyLittleHomepage.v2.post.domain.query.vo.PostSearchVo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslSearchedPostRepository {

    Page<QueriedPost> getPosts(PostSearchVo postSearchVo, Pageable pageable);

    Page<QueriedPost> getPostsWithCursor(PostSearchVo postSearchVo, boolean isAsc, Long cursor,
            Pageable pageable);

    List<QueriedPost> getOrderedPostsLimit(Long boardId, PostOrderType postOrderType, int limit);
}
