package com.yunseojin.MyLittleHomepage.v2.application.feedback.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command.PostFeedbackCommand;
import com.yunseojin.MyLittleHomepage.v2.application.feedback.mapper.FeedbackMapper;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model.FeedbackService;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.model.PostFeedback;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.command.repository.PostFeedbackRepository;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.vo.FeedbackType;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class PostFeedbackCommandHandler implements
        CommandHandler<PostFeedbackCommand, FeedbackType> {

    private final FeedbackService service;

    private final PostFeedbackRepository repository;

    private final FeedbackMapper mapper;

    @Override
    public FeedbackType handle(PostFeedbackCommand command) {

        var feedback = createOrApplyFeedback(command);
        return repository.save(feedback).getFeedbackType();
    }

    private PostFeedback createOrApplyFeedback(PostFeedbackCommand command) {
        var feedback = repository.getByWriterIdAndPostId(command.getWriterId(),
                command.getPostId());
        if (Objects.isNull(feedback)) {
            return (PostFeedback) service.create(mapper.from(command));
        }
        return (PostFeedback) service.apply(feedback, command.getFeedbackType());
    }
}
