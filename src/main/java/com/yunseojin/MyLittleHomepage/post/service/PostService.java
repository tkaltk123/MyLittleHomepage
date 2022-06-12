package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import org.springframework.data.domain.Page;

public interface PostService {
    PostResponse createPost(Long boardId, PostRequest postRequest);

    PostResponse updatePost(Long postId, PostRequest postRequest);

    Long deletePost(Long postId);

    PostResponse getPost(Long postId);

    Page<PostResponse> getPostList(Long boardId, PostSearch postSearch);

}
