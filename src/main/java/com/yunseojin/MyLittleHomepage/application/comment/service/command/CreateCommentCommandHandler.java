package com.yunseojin.MyLittleHomepage.application.comment.service.command;

import com.yunseojin.MyLittleHomepage.application.comment.dto.command.CreateCommentCommand;
import com.yunseojin.MyLittleHomepage.application.comment.dto.response.CommentResponse;
import com.yunseojin.MyLittleHomepage.application.comment.mapper.CommentMapper;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.domain.comment.command.model.CommentService;
import com.yunseojin.MyLittleHomepage.domain.comment.command.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreateCommentCommandHandler implements
        CommandHandler<CreateCommentCommand, CommentResponse> {

    private final CommentService service;

    private final CommentRepository repository;

    private final CommentMapper mapper;

    @Override
    public CommentResponse handle(CreateCommentCommand command) {
        var post = service.create(mapper.from(command));
        return mapper.toResponse(repository.save(post));
    }
}
