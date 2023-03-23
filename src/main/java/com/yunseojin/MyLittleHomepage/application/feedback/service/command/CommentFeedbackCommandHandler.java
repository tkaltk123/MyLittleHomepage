package com.yunseojin.MyLittleHomepage.application.feedback.service.command;

import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.comment.CommentLikeCommand;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.response.FeedbackResponse;
import com.yunseojin.MyLittleHomepage.application.feedback.mapper.FeedbackMapper;
import com.yunseojin.MyLittleHomepage.domain.feedback.command.model.CommentFeedback;
import com.yunseojin.MyLittleHomepage.domain.feedback.command.model.FeedbackService;
import com.yunseojin.MyLittleHomepage.domain.feedback.command.repository.CommentFeedbackRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentFeedbackCommandHandler implements
        CommandHandler<CommentLikeCommand, FeedbackResponse> {

    private final FeedbackService service;

    private final CommentFeedbackRepository repository;

    private final FeedbackMapper mapper;

    @Override
    public FeedbackResponse handle(CommentLikeCommand command) {

        var feedback = createOrApplyFeedback(command);
        feedback = repository.save(feedback);
        return new FeedbackResponse(feedback.getFeedbackType());
    }

    private CommentFeedback createOrApplyFeedback(CommentLikeCommand command) {
        var feedback = repository.getByWriterIdAndCommentId(command.getWriterId(),
                command.getCommentId());
        if (Objects.isNull(feedback)) {
            return (CommentFeedback) service.create(mapper.from(command));
        }
        return (CommentFeedback) service.apply(feedback, command.getFeedbackType());
    }
}
