package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.Comment;


public class CommentCreatedEvent extends CommentEvent {

    public CommentCreatedEvent(Comment comment) {
        super(comment);
    }
}
