package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.MemberDeleteCommand;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberDeleteCommandHandler implements CommandHandler<MemberDeleteCommand, Void> {

    private final MemberService service;

    private final MemberRepository repository;

    @Override
    public Void handle(MemberDeleteCommand command) {
        Member member = command.getMember();
        service.delete(member, command.getCurrentPassword());
        repository.delete(member);
        return null;
    }
}
