package com.yunseojin.MyLittleHomepage.v2.post.domain.event;

import com.yunseojin.MyLittleHomepage.v2.contract.domain.event.DomainEvent;
import com.yunseojin.MyLittleHomepage.v2.post.domain.model.Post;
import lombok.Getter;

@Getter
public abstract class PostEvent implements DomainEvent<Post> {

    protected final Post aggregate;

    protected PostEvent(Post post) {
        this.aggregate = post;
    }
}
