package com.yunseojin.MyLittleHomepage.application.member.service.command;

import com.yunseojin.MyLittleHomepage.domain.member.query.model.QueriedMember;
import com.yunseojin.MyLittleHomepage.application.contract.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.application.member.dto.command.UpdateMemberCommand;
import com.yunseojin.MyLittleHomepage.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.application.member.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.Member;
import com.yunseojin.MyLittleHomepage.domain.member.command.model.MemberService;
import com.yunseojin.MyLittleHomepage.domain.member.command.repository.MemberRepository;
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

    private Member updateMember(Long memberId, QueriedMember memberInfo) {
        var member = repository.getById(memberId);

        return repository.save(service.update(member, memberInfo));
    }
}
