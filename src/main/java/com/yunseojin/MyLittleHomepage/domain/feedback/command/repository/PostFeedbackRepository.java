package com.yunseojin.MyLittleHomepage.domain.feedback.command.repository;

import com.yunseojin.MyLittleHomepage.domain.feedback.command.model.PostFeedback;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFeedbackRepository extends FeedbackRepository<PostFeedback> {

    PostFeedback getByWriterIdAndPostId(Long writerId, Long postId);
}