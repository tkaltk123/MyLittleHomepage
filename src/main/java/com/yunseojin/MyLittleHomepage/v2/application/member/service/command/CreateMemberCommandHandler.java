package com.yunseojin.MyLittleHomepage.v2.application.member.service.command;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.command.CreateMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.application.member.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.v2.domain.member.command.model.Member;
import com.yunseojin.MyLittleHomepage.v2.domain.member.command.model.MemberService;
import com.yunseojin.MyLittleHomepage.v2.domain.member.command.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class CreateMemberCommandHandler implements
        CommandHandler<CreateMemberCommand, MemberResponse> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    @Override
    public MemberResponse handle(CreateMemberCommand command) {
        return mapper.toResponse(createMember(command));
    }

    private Member createMember(CreateMemberCommand command) {
        var member = service.create(mapper.from(command));
        return repository.save(member);
    }

}
