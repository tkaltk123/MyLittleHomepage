package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.post.dto.FullPostSearch;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DslPostRepository {

    Page<PostEntity> getPosts(BoardEntity board, Pageable pageable, PostSearch postSearch);

    Page<PostEntity> getPostsWithCursor(Long lastPostId, Pageable pageable, FullPostSearch postSearch);

    List<PostEntity> getPostsOrderedBy(BoardEntity board, int postCount, PostOrderType postOrderType);
}
