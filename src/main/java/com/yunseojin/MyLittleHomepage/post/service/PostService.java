package com.yunseojin.MyLittleHomepage.post.service;

import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostResponse createPost(Long boardId, PostRequest postRequest);

    PostResponse updatePost(Long postId, PostRequest postRequest);

    void deletePost(Long postId);

    PostResponse getPost(Long postId);

    List<PostResponse> getPostList(Long boardId, Integer page);

}
