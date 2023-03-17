package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.DeleteMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.MemberService;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeleteMemberCommandHandler implements CommandHandler<DeleteMemberCommand, Void> {

    private final MemberService service;

    private final MemberRepository repository;

    @Override
    public Void handle(DeleteMemberCommand command) {
        var member = repository.getById(command.getMemberId());
        service.delete(member, command.getCurrentPassword());
        repository.delete(member);
        return null;
    }
}
