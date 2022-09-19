package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InternalMemberServiceImpl implements InternalMemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity getMemberById(Long memberId) {

        var optMember = memberRepository.findById(memberId);

        if (optMember.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);

        return optMember.get();
    }

    @Override
    public MemberEntity getMemberByLoginId(String loginId) {

        var optMember = memberRepository.findByLoginId(loginId);

        if (optMember.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);

        return optMember.get();
    }
}
