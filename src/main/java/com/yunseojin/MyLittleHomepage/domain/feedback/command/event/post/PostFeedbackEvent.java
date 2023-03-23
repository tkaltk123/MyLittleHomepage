package com.yunseojin.MyLittleHomepage.domain.feedback.command.event.post;

import com.yunseojin.MyLittleHomepage.domain.feedback.command.event.FeedbackEvent;
import lombok.Getter;

@Getter
public abstract class PostFeedbackEvent extends FeedbackEvent {

    protected PostFeedbackEvent(Object payload) {
        super(payload);
    }
}
