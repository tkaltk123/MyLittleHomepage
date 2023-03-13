package com.yunseojin.MyLittleHomepage.v2.member.application.command.create;

import com.yunseojin.MyLittleHomepage.v2.auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.command.CommandService;
import com.yunseojin.MyLittleHomepage.v2.member.application.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.member.domain.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.v2.member.domain.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberCreateService implements CommandService<MemberCreateCommand, Token> {

    private final MemberDomainService domainService;

    private final JwtTokenProvider tokenProvider;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    @Override
    public Token handle(MemberCreateCommand command) {
        var member = repository.save(createMember(command));
        var token = tokenProvider.createToken(member);
        return new Token(token.getAccessToken(), token.getRefreshToken());
    }

    private Member createMember(MemberCreateCommand command) {
        return domainService.create(mapper.from(command));
    }
}
