package com.yunseojin.MyLittleHomepage.v2.post.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.command.DeletePostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.domain.repository.PostRepositoryV2;
import com.yunseojin.MyLittleHomepage.v2.post.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeletePostCommandHandler implements CommandHandler<DeletePostCommand, Void> {

    private final PostService service;

    private final PostRepositoryV2 repository;

    @Override
    public Void handle(DeletePostCommand command) {
        var post = repository.getById(command.getPostId());
        service.delete(post, command.getMember());
        repository.delete(post);
        return null;
    }
}
