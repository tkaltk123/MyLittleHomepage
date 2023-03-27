package com.yunseojin.MyLittleHomepage.application.board.service.command;

import com.yunseojin.MyLittleHomepage.application.board.dto.command.PostCountIncreaseCommand;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.domain.board.command.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class PostCountIncreaseCommandHandler implements
        CommandHandler<PostCountIncreaseCommand, Void> {

    private final BoardRepository repository;

    @Override
    public Void handle(PostCountIncreaseCommand command) {

        repository.increasePostCount(command.getBoardId());
        return null;
    }
}
