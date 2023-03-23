package com.yunseojin.MyLittleHomepage.v2.application.post.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.command.CreatePostCommand;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.application.post.mapper.PostMapperV2;
import com.yunseojin.MyLittleHomepage.v2.domain.post.command.model.PostService;
import com.yunseojin.MyLittleHomepage.v2.domain.post.command.repository.PostRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreatePostCommandHandler implements CommandHandler<CreatePostCommand, PostResponse> {

    private final PostService service;

    private final PostRepositoryV2 repository;

    private final PostMapperV2 mapper;

    @Override
    public PostResponse handle(CreatePostCommand command) {
        var post = service.create(mapper.from(command));

        return mapper.toResponse(repository.save(post));
    }

}
