package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.Comment;


public class CommentUpdatedEvent extends CommentEvent {

    public CommentUpdatedEvent(Comment comment) {
        super(comment);
    }
}
