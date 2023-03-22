package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.UpdateMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.application.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.aggregate.MemberService;
import com.yunseojin.MyLittleHomepage.v2.member.domain.command.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
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
        var member = command.getMember();
        service.validatePassword(member, command.getCurrentPassword());

        return mapper.toResponse(updateMember(member.getId(), mapper.from(command)));
    }

    private QueriedMember updateMember(Long memberId, QueriedMember memberInfo) {
        var member = repository.getById(memberId);
        member = repository.save(service.update(member, memberInfo));

        return member.readOnly();
    }
}
