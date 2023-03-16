package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import auth.dto.Token;
import auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.CreateMemberCommand;
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
public class CreateMemberCommandHandler implements CommandHandler<CreateMemberCommand, Token> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    private final JwtTokenProvider tokenProvider;

    @Override
    public Token handle(CreateMemberCommand command) {
        var member = repository.save(createMember(command));
        var token = tokenProvider.createToken(member);
        return new Token(token.getAccessToken(), token.getRefreshToken());
    }

    private Member createMember(CreateMemberCommand command) {
        return service.create(mapper.from(command));
    }
}
