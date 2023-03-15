package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import com.yunseojin.MyLittleHomepage.v2.auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.MemberCreateCommand;
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
public class MemberCreateCommandHandler implements CommandHandler<MemberCreateCommand, Token> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    private final JwtTokenProvider tokenProvider;

    @Override
    public Token handle(MemberCreateCommand command) {
        var member = repository.save(createMember(command));
        var token = tokenProvider.createToken(member);
        return new Token(token.getAccessToken(), token.getRefreshToken());
    }

    private Member createMember(MemberCreateCommand command) {
        return service.create(mapper.from(command));
    }
}
