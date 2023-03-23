package com.yunseojin.MyLittleHomepage.application.post.service.command;

import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.application.post.dto.command.CreatePostCommand;
import com.yunseojin.MyLittleHomepage.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.application.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.domain.post.command.model.PostService;
import com.yunseojin.MyLittleHomepage.domain.post.command.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreatePostCommandHandler implements CommandHandler<CreatePostCommand, PostResponse> {

    private final PostService service;

    private final PostRepository repository;

    private final PostMapper mapper;

    @Override
    public PostResponse handle(CreatePostCommand command) {
        var post = service.create(mapper.from(command));

        return mapper.toResponse(repository.save(post));
    }

}
