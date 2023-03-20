package com.yunseojin.MyLittleHomepage.v2.comment.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.command.DeleteCommentCommand;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.aggregete.CommentService;
import com.yunseojin.MyLittleHomepage.v2.comment.domain.command.repository.CommentRepositoryV2;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeleteCommentCommandHandler implements CommandHandler<DeleteCommentCommand, Void> {

    private final CommentService service;

    private final CommentRepositoryV2 repository;

    @Override
    public Void handle(DeleteCommentCommand command) {
        var comment = repository.getById(command.getCommentId());
        service.delete(comment, command.getMemberId());
        repository.delete(comment);
        return null;
    }
}
