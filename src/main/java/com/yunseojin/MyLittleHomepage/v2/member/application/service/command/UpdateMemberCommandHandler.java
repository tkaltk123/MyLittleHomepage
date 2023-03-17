package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.UpdateMemberCommand;
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
public class UpdateMemberCommandHandler implements CommandHandler<UpdateMemberCommand, String> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String handle(UpdateMemberCommand command) {
        var member = updateMember(command);
        var token = jwtTokenProvider.createToken(member);
        return token.getAccessToken();
    }

    private Member updateMember(UpdateMemberCommand command) {
        var member = service.update(
                repository.getById(command.getMemberId()),
                mapper.from(command),
                command.getCurrentPassword());
        return repository.save(member);
    }
}
