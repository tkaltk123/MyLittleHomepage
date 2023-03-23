package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.comment;

import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.FeedbackEvent;
import lombok.Getter;

@Getter
public abstract class CommentFeedbackEvent extends FeedbackEvent {


    protected CommentFeedbackEvent(Object payload) {
        super(payload);
    }
}
