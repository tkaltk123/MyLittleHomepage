package com.yunseojin.MyLittleHomepage.application.post.subscriber;

import com.yunseojin.MyLittleHomepage.application.board.dto.command.PostCountDecreaseCommand;
import com.yunseojin.MyLittleHomepage.application.board.dto.command.PostCountIncreaseCommand;
import com.yunseojin.MyLittleHomepage.application.contract.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.domain.post.command.event.PostCreatedEvent;
import com.yunseojin.MyLittleHomepage.domain.post.command.event.PostDeletedEvent;
import com.yunseojin.MyLittleHomepage.domain.post.query.model.QueriedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSubscriber {

    private final ApplicationService applicationService;

    @EventListener
    public void onPostCreated(PostCreatedEvent event) {
        var post = (QueriedPost) event.getPayload();
        applicationService.executeCommand(new PostCountIncreaseCommand(post.getBoardId()));
    }

    @EventListener
    public void onPostDeleted(PostDeletedEvent event) {
        var post = (QueriedPost) event.getPayload();
        applicationService.executeCommand(new PostCountDecreaseCommand(post.getBoardId()));
    }

}
