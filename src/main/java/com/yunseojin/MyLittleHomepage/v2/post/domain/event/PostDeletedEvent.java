package com.yunseojin.MyLittleHomepage.v2.post.domain.event;

import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;


public class PostDeletedEvent extends PostEvent {

    public PostDeletedEvent(Post post) {
        super(post);
    }
}
