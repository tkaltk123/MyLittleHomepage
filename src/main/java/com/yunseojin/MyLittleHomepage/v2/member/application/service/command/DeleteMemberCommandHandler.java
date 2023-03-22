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
        var member = command.getMember();
        service.validatePassword(member, command.getCurrentPassword());

        return deleteMember(member.getId());
    }

    private Void deleteMember(Long memberId) {
        var member = repository.getById(memberId);
        service.delete(member);
        repository.delete(member);
        return null;
    }
}
