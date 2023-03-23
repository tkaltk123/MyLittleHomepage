package com.yunseojin.MyLittleHomepage.v2.application.comment.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.comment.dto.command.UpdateCommentCommand;
import com.yunseojin.MyLittleHomepage.v2.application.comment.dto.response.CommentResponse;
import com.yunseojin.MyLittleHomepage.v2.application.comment.mapper.CommentMapperV2;
import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.model.CommentService;
import com.yunseojin.MyLittleHomepage.v2.domain.comment.command.repository.CommentRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UpdateCommentCommandHandler implements
        CommandHandler<UpdateCommentCommand, CommentResponse> {

    private final CommentService service;

    private final CommentRepositoryV2 repository;

    private final CommentMapperV2 mapper;

    @Override
    public CommentResponse handle(UpdateCommentCommand command) {
        var comment = repository.getById(command.getCommentId());
        comment = service.update(comment, mapper.from(command));
        return mapper.toResponse(repository.save(comment));
    }
}
