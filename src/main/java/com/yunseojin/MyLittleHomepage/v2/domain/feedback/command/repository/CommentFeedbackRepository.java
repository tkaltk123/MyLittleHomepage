package com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.repository;

import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model.CommentFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentFeedbackRepository extends FeedbackRepository<CommentFeedback> {

    CommentFeedback getByWriterIdAndCommentId(Long writerId, Long commentId);
}