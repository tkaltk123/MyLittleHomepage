package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    PostResponse createPost(Long boardId, PostRequest postRequest);

    PostResponse updatePost(Long postId, PostRequest postRequest);

    void deletePost(Long postId);

    PostResponse getPost(Long postId);

    Page<PostResponse> getPostList(Long boardId, int postCount, PostSearch postSearch);

    List<PostResponse> getOrderedPostList(Long boardId, int postCount, PostOrderType postOrderType);

}
