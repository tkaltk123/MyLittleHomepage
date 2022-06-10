package com.yunseojin.MyLittleHomepage.post.repository;

import com.yunseojin.MyLittleHomepage.board.entity.BoardEntity;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslPostRepository {
    Page<PostEntity> getPosts(BoardEntity board, Pageable pageable, PostSearch postSearch);

    PostEntity getPost(Long postId);
}
