package com.yunseojin.MyLittleHomepage.application.board.service.command;

import com.yunseojin.MyLittleHomepage.application.board.dto.command.PostCountDecreaseCommand;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.domain.board.command.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class PostCountDecreaseCommandHandler implements
        CommandHandler<PostCountDecreaseCommand, Void> {

    private final BoardRepository repository;

    @Override
    public Void handle(PostCountDecreaseCommand command) {

        repository.decreasePostCount(command.getBoardId());
        return null;
    }
}
