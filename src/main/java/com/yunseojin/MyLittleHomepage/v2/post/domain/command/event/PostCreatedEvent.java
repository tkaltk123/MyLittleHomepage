package com.yunseojin.MyLittleHomepage.v2.post.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;


public class PostCreatedEvent extends PostEvent {

    public PostCreatedEvent(Post post) {
        super(post);
    }
}
