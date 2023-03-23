package com.yunseojin.MyLittleHomepage.application.feedback.dto.command.comment;

import com.yunseojin.MyLittleHomepage.domain.feedback.vo.FeedbackType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentLikeCommand extends CommentFeedbackCommand {

    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.LIKE;
    }

    public CommentLikeCommand(Long commentId) {
        super(commentId);
    }
}
