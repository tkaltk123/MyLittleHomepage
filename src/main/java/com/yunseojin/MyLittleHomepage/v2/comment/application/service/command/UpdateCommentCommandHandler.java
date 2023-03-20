package com.yunseojin.MyLittleHomepage.v2.comment.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.command.UpdateCommentCommand;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponse;
import com.yunseojin.MyLittleHomepage.v2.comment.application.mapper.CommentMapperV2;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.CommentService;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.repository.CommentRepositoryV2;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
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
