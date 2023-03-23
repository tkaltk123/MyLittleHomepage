package com.yunseojin.MyLittleHomepage.application.feedback.dto.command.post;

import com.yunseojin.MyLittleHomepage.domain.feedback.vo.FeedbackType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PostLikeCommand extends PostFeedbackCommand {

    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.LIKE;
    }

    public PostLikeCommand(Long postId) {
        super(postId);
    }
}
