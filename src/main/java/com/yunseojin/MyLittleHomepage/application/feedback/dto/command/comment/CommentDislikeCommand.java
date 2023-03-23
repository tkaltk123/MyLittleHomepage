package com.yunseojin.MyLittleHomepage.application.feedback.dto.command.comment;

import com.yunseojin.MyLittleHomepage.domain.feedback.vo.FeedbackType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentDislikeCommand extends CommentFeedbackCommand {

    @Override
    public FeedbackType getFeedbackType() {
        return FeedbackType.LIKE;
    }

    public CommentDislikeCommand(Long commentId) {
        super(commentId);
    }
}
