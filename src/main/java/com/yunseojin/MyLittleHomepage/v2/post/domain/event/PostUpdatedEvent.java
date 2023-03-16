package com.yunseojin.MyLittleHomepage.v2.post.domain.event;

import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;


public class PostUpdatedEvent extends PostEvent {

    public PostUpdatedEvent(Post post) {
        super(post);
    }
}
