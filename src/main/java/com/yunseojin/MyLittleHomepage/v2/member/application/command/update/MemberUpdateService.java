package com.yunseojin.MyLittleHomepage.v2.member.application.command.update;

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
public class MemberUpdateService implements CommandService<MemberUpdateCommand, String> {

    private final MemberDomainService domainService;

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository repository;

    private final MemberMapper mapper;

    @Override
    public String handle(MemberUpdateCommand command) {
        var member = repository.save(updateMember(command));
        var token = jwtTokenProvider.createToken(member);
        return token.getAccessToken();
    }

    private Member updateMember(MemberUpdateCommand command) {
        return domainService.update(
                command.getMember(),
                mapper.from(command),
                command.getCurrentPassword());
    }
}
