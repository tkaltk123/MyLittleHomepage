package com.yunseojin.MyLittleHomepage.application.feedback.mapper;

import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.comment.CommentFeedbackCommand;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.post.PostFeedbackCommand;
import com.yunseojin.MyLittleHomepage.domain.feedback.query.model.QueriedCommentFeedback;
import com.yunseojin.MyLittleHomepage.domain.feedback.query.model.QueriedPostFeedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface FeedbackMapper {

    QueriedPostFeedback from(PostFeedbackCommand command);

    QueriedCommentFeedback from(CommentFeedbackCommand command);
}
