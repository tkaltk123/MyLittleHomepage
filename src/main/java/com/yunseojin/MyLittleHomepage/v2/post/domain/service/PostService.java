package com.yunseojin.MyLittleHomepage.v2.post.domain.service;

import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;
import com.yunseojin.MyLittleHomepage.v2.post.domain.model.PostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    public Post create(PostVo post) {
        return new Post(post);
    }

    public Post update(Post post, PostVo postVo) {
        post.update(postVo);
        return post;
    }

    public void delete(Post post) {
        post.delete();
    }
}
