package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.post;

import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.event.FeedbackEvent;
import lombok.Getter;

@Getter
public abstract class PostFeedbackEvent extends FeedbackEvent {

    protected PostFeedbackEvent(Object payload) {
        super(payload);
    }
}
