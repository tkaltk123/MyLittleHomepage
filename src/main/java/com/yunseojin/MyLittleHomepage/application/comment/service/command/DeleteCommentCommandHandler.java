package com.yunseojin.MyLittleHomepage.application.comment.service.command;

import com.yunseojin.MyLittleHomepage.application.comment.dto.command.DeleteCommentCommand;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.domain.comment.command.model.CommentService;
import com.yunseojin.MyLittleHomepage.domain.comment.command.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeleteCommentCommandHandler implements CommandHandler<DeleteCommentCommand, Void> {

    private final CommentService service;

    private final CommentRepository repository;

    @Override
    public Void handle(DeleteCommentCommand command) {
        var comment = repository.getById(command.getCommentId());
        service.delete(comment, command.getMemberId());
        repository.delete(comment);
        return null;
    }
}
