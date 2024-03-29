package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.post.dto.FullPostSearch;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    PostResponse createPost(Long memberId, Long boardId, PostRequest postRequest);

    PostResponse updatePost(Long memberId, Long postId, PostRequest postRequest);

    void deletePost(Long memberId, Long postId);

    PostResponse getPost(Long postId);

    void viewPost(String ip, Long postId);

    Page<PostResponse> getPostList(Long boardId, int postCount, PostSearch postSearch);

    Page<PostResponse> getPostListWithCursor(Long lastPostId, int postCount, FullPostSearch postSearch);

    List<PostResponse> getOrderedPostList(Long boardId, int postCount, PostOrderType postOrderType);

}
