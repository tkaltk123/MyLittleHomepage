package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.MemberUpdateCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberUpdateCommandHandler implements CommandHandler<MemberUpdateCommand, String> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String handle(MemberUpdateCommand command) {
        var member = repository.save(updateMember(command));
        var token = jwtTokenProvider.createToken(member);
        return token.getAccessToken();
    }

    private Member updateMember(MemberUpdateCommand command) {
        return service.update(
                command.getMember(),
                mapper.from(command),
                command.getCurrentPassword());
    }
}
