package com.yunseojin.MyLittleHomepage.v2.post.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;


public class PostUpdatedEvent extends PostEvent {

    public PostUpdatedEvent(Post post) {
        super(post);
    }
}
