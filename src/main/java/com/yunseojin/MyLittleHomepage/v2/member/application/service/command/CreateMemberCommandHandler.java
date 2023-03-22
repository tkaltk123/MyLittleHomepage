package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.CreateMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.application.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.MemberService;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.repository.MemberRepository;
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
        var member = createMember(command);
        return mapper.toResponse(member.readOnly());
    }

    private Member createMember(CreateMemberCommand command) {
        var member = service.create(mapper.from(command));
        return repository.save(member);
    }

}
