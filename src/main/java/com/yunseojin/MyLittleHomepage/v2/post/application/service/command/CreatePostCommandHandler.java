package com.yunseojin.MyLittleHomepage.v2.post.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.board.domain.command.repository.BoardRepositoryV2;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.command.CreatePostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.post.application.mapper.PostMapperV2;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.Post;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.aggregete.PostService;
import com.yunseojin.MyLittleHomepage.v2.post.domain.command.repository.PostRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreatePostCommandHandler implements CommandHandler<CreatePostCommand, PostResponse> {

    private final PostService service;

    private final PostRepositoryV2 repository;

    private final BoardRepositoryV2 boardRepository;

    private final PostMapperV2 mapper;

    @Override
    public PostResponse handle(CreatePostCommand command) {
        var post = service.create(mapper.from(command));
        return toResponse(repository.save(post));
    }

    private PostResponse toResponse(Post post) {
        var board = boardRepository.getById(post.getBoardId());

        return mapper.toResponse(post, board.getName());
    }
}
