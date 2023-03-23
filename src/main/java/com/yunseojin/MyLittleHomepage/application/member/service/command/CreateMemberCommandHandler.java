package com.yunseojin.MyLittleHomepage.application.member.service.command;

import com.yunseojin.MyLittleHomepage.application.member.dto.command.CreateMemberCommand;
import com.yunseojin.MyLittleHomepage.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.application.member.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.MemberService;
import com.yunseojin.MyLittleHomepage.domain.member.command.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.Member;
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
