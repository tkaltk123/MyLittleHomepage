package com.yunseojin.MyLittleHomepage.v2.post.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;


public class PostDeletedEvent extends PostEvent {

    public PostDeletedEvent(Post post) {
        super(post);
    }
}
