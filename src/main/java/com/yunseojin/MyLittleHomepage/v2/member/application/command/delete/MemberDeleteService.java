package com.yunseojin.MyLittleHomepage.v2.member.application.command.delete;

import com.yunseojin.MyLittleHomepage.v2.contract.application.command.CommandService;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberDeleteService implements CommandService<MemberDeleteCommand, Void> {

    private final MemberDomainService domainService;

    private final MemberRepository repository;

    @Override
    public Void handle(MemberDeleteCommand command) {
        Member member = command.getMember();
        domainService.delete(member, command.getCurrentPassword());
        repository.delete(member);
        return null;
    }
}
