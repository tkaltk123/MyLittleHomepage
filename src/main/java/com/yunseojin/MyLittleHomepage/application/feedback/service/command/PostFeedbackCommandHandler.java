package com.yunseojin.MyLittleHomepage.application.feedback.service.command;

import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.post.PostFeedbackCommand;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.response.FeedbackResponse;
import com.yunseojin.MyLittleHomepage.application.feedback.mapper.FeedbackMapper;
import com.yunseojin.MyLittleHomepage.domain.feedback.command.model.FeedbackService;
import com.yunseojin.MyLittleHomepage.domain.feedback.command.model.PostFeedback;
import com.yunseojin.MyLittleHomepage.domain.feedback.command.repository.PostFeedbackRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class PostFeedbackCommandHandler implements
        CommandHandler<PostFeedbackCommand, FeedbackResponse> {

    private final FeedbackService service;

    private final PostFeedbackRepository repository;

    private final FeedbackMapper mapper;

    @Override
    public FeedbackResponse handle(PostFeedbackCommand command) {

        var feedback = createOrApplyFeedback(command);
        feedback = repository.save(feedback);
        return new FeedbackResponse(feedback.getFeedbackType());
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
