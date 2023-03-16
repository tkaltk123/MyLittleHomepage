package com.yunseojin.MyLittleHomepage.v2.post.domain.event;

import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;


public class PostCreatedEvent extends PostEvent {

    public PostCreatedEvent(Post post) {
        super(post);
    }
}
