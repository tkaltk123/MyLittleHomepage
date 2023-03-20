package com.yunseojin.MyLittleHomepage.v2.comment.domain.command.event;

import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.Comment;


public class CommentDeletedEvent extends CommentEvent {

    public CommentDeletedEvent(Comment comment) {
        super(comment);
    }
}
