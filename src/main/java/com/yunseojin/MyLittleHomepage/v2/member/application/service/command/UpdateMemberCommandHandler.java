package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.UpdateMemberCommand;
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
public class UpdateMemberCommandHandler implements
        CommandHandler<UpdateMemberCommand, MemberResponse> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    @Override
    public MemberResponse handle(UpdateMemberCommand command) {
        var member = updateMember(command);
        return mapper.toResponse(member);
    }

    private Member updateMember(UpdateMemberCommand command) {
        var member = service.update(
                repository.getById(command.getMemberId()),
                mapper.from(command),
                command.getCurrentPassword());
        return repository.save(member);
    }
}
