package com.yunseojin.MyLittleHomepage.v2.application.feedback.mapper;

import com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command.CommentFeedbackCommand;
import com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command.PostFeedbackCommand;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedCommentFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.query.model.QueriedPostFeedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface FeedbackMapper {

    QueriedPostFeedback from(PostFeedbackCommand command);

    QueriedCommentFeedback from(CommentFeedbackCommand command);
}
