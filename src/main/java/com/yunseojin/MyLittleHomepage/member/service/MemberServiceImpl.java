package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.etc.exception.BaseException;
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
    public void resister(MemberRequest memberRequest) {
        //이미 로그인 되어있을 경우
        if (memberInfo.getId() != null)
            throw new BadRequestException(ErrorMessage.ALREADY_LOGIN_EXCEPTION);
        //엔티티 생성
        var member = MemberEntity.builder()
                .loginId(memberRequest.getLoginId())
                .password(PasswordUtil.getHashedPassword(memberRequest.getPassword()))
                .nickname(memberRequest.getNickname())
                .memberType(MemberType.NORMAL)
                .build();
        //로그인 ID 중복
        if (memberRepository.existsByLoginId(member.getLoginId()))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);
        //닉네임 중복
        if (memberRepository.existsByNickname(member.getNickname()))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
        //이상 없으면 저장
        memberRepository.save(member);
        //세션 저장
        memberInfo.setId(member.getId());
        memberInfo.setLoginId(member.getLoginId());
    }

    @Override
    public void modify(MemberRequest memberRequest) {
        return;
    }

    @Override
    public void delete(MemberRequest memberRequest) {
        return;
    }

    @Override
    public void login(MemberRequest memberRequest) {
        var member = memberRepository.findByLoginId(memberRequest.getLoginId());
        if (member == null)
            return;
        if (!PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword()))
            return;
        memberInfo.setId(member.getId());
        memberInfo.setLoginId(member.getLoginId());
        return;
    }

    @Override
    public void logout() {
        return;
    }

}
