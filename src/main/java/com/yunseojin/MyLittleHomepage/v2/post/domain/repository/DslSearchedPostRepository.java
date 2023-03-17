package com.yunseojin.MyLittleHomepage.v2.post.domain.repository;

import com.yunseojin.MyLittleHomepage.v2.post.domain.SearchedPost;
import com.yunseojin.MyLittleHomepage.v2.post.domain.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.v2.post.domain.vo.PostSearchVo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslSearchedPostRepository {

    Page<SearchedPost> getPosts(PostSearchVo postSearchVo, Pageable pageable);

    Page<SearchedPost> getPostsWithCursor(PostSearchVo postSearchVo, boolean isAsc, Long cursor,
            Pageable pageable);

    List<SearchedPost> getOrderedPostsLimit(Long boardId, PostOrderType postOrderType, int limit);
}
