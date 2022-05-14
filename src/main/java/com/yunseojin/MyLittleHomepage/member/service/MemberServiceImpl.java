package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Resource
    private MemberInfo memberInfo;

    @Override
    public boolean resister(MemberRequest memberRequest) {
        var member = MemberEntity.builder()
                .loginId(memberRequest.getLoginId())
                .password(PasswordUtil.getHashedPassword(memberRequest.getPassword()))
                .nickname(memberRequest.getNickname())
                .memberType(MemberType.NORMAL)
                .build();
        if (memberRepository.existsByLoginId(member.getLoginId()) || memberRepository.existsByNickname(member.getNickname()))
            return false;
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean login(MemberRequest memberRequest) {
        var member = memberRepository.findByLoginId(memberRequest.getLoginId());
        if (member == null)
            return false;
        if (!PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword()))
            return false;
        memberInfo.setId(member.getId());
        memberInfo.setLoginId(member.getLoginId());
        return true;
    }

    @Override
    public String get() {
        return memberInfo.toString();
    }
}
