package com.yunseojin.MyLittleHomepage.application.member.service.command;

import com.yunseojin.MyLittleHomepage.application.member.dto.command.DeleteMemberCommand;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.MemberService;
import com.yunseojin.MyLittleHomepage.domain.member.command.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
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
