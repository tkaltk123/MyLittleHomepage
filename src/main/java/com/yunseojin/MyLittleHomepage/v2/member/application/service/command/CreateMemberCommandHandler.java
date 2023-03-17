package com.yunseojin.MyLittleHomepage.v2.member.application.service.command;

import auth.dto.Token;
import auth.service.JwtTokenProvider;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.CommandHandler;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.CreateMemberCommand;
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
public class CreateMemberCommandHandler implements CommandHandler<CreateMemberCommand, Token> {

    private final MemberService service;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    private final JwtTokenProvider tokenProvider;

    @Override
    public Token handle(CreateMemberCommand command) {
        var member = createMember(command);
        return tokenProvider.createToken(member);
    }

    private Member createMember(CreateMemberCommand command) {
        var member = service.create(mapper.from(command));
        return repository.save(member);
    }
}
