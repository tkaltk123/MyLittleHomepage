package com.yunseojin.MyLittleHomepage.application.post.service.command;

import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.application.post.dto.command.DeletePostCommand;
import com.yunseojin.MyLittleHomepage.domain.post.command.model.PostService;
import com.yunseojin.MyLittleHomepage.domain.post.command.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeletePostCommandHandler implements CommandHandler<DeletePostCommand, Void> {

    private final PostService service;

    private final PostRepository repository;

    @Override
    public Void handle(DeletePostCommand command) {
        var post = repository.getById(command.getPostId());
        service.delete(post, command.getMemberId());
        repository.delete(post);
        return null;
    }
}
