package com.yunseojin.MyLittleHomepage.domain.feedback.command.repository;

import com.yunseojin.MyLittleHomepage.domain.feedback.command.model.CommentFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentFeedbackRepository extends FeedbackRepository<CommentFeedback> {

    CommentFeedback getByWriterIdAndCommentId(Long writerId, Long commentId);
}