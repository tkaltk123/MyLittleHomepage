package com.yunseojin.MyLittleHomepage.v2.post.domain.repository;

import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.post.dto.FullPostSearch;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DslPostRepository {

    Page<Post> getPosts(Long boardId, Pageable pageable, PostSearch postSearch);

    Page<Post> getPostsWithCursor(Long lastPostId, Pageable pageable, FullPostSearch postSearch);

    List<Post> getPostsOrderedBy(Long boardId, int postCount, PostOrderType postOrderType);
}
