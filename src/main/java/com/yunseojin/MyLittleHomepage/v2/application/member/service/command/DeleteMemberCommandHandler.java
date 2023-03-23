package com.yunseojin.MyLittleHomepage.v2.application.member.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.command.DeleteMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.domain.member.command.model.MemberService;
import com.yunseojin.MyLittleHomepage.v2.domain.member.command.repository.MemberRepository;
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
