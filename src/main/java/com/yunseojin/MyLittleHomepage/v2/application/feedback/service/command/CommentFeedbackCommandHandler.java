package com.yunseojin.MyLittleHomepage.v2.application.feedback.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command.CommentFeedbackCommand;
import com.yunseojin.MyLittleHomepage.v2.application.feedback.mapper.FeedbackMapper;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model.CommentFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model.FeedbackService;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.repository.CommentFeedbackRepository;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.vo.FeedbackType;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentFeedbackCommandHandler implements
        CommandHandler<CommentFeedbackCommand, FeedbackType> {

    private final FeedbackService service;

    private final CommentFeedbackRepository repository;

    private final FeedbackMapper mapper;

    @Override
    public FeedbackType handle(CommentFeedbackCommand command) {

        var feedback = createOrApplyFeedback(command);
        return repository.save(feedback).getFeedbackType();
    }

    private CommentFeedback createOrApplyFeedback(CommentFeedbackCommand command) {
        var feedback = repository.getByWriterIdAndCommentId(command.getWriterId(),
                command.getCommentId());
        if (Objects.isNull(feedback)) {
            return (CommentFeedback) service.create(mapper.from(command));
        }
        return (CommentFeedback) service.apply(feedback, command.getFeedbackType());
    }
}
