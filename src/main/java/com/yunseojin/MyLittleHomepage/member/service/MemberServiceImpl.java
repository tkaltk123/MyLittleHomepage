package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.dto.MemberResponse;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Resource
    private MemberInfo memberInfo;

    @Override
    @Login(required = false)
    public MemberResponse resister(MemberRequest memberRequest) {
        checkLogInIdDuplicate(memberRequest.getLoginId());
        checkNicknameDuplicate(memberRequest.getNickname());

        var member = MemberMapper.INSTANCE.toMemberEntity(memberRequest);
        member.setMemberType(MemberType.NORMAL);

        memberRepository.save(member);
        memberInfo.setMember(member);

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    @Override
    @Login
    public MemberResponse modify(MemberRequest memberRequest) {
        var member = memberRepository.getMember(memberInfo.getId());
        var loginId = memberRequest.getLoginId();
        var nickname = memberRequest.getNickname();
        var password = memberRequest.getPassword();

        checkLogInIdDuplicate(member, loginId);
        checkNicknameDuplicate(member, nickname);

        if (loginId != null) {
            member.setLoginId(loginId);
            memberInfo.setMember(member);
        }

        if (nickname != null)
            member.setNickname(nickname);

        if (password != null)
            member.setPassword(PasswordUtil.getHashedPassword(password));

        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    @Login
    @Override
    public void delete(MemberRequest memberRequest) {
        var member = memberRepository.getMember(memberInfo.getId());
        PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword());

        memberRepository.delete(member);
        member.setIsDeleted(1);
        memberInfo.clear();
    }

    @Login(required = false)
    @Override
    public MemberResponse login(MemberRequest memberRequest) {
        var member = memberRepository.getMember(memberRequest.getLoginId());
        PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword());
        memberInfo.setMember(member);
        return MemberMapper.INSTANCE.toMemberResponse(member);
    }

    @Login
    @Override
    public void logout() {
        memberInfo.clear();
    }

    private void checkLogInIdDuplicate(String newLogInId) {
        if (memberRepository.existsByLoginId(newLogInId))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);
    }

    private void checkLogInIdDuplicate(MemberEntity member, String newLogInId) {
        if (newLogInId != null && !member.getLoginId().equals(newLogInId) && memberRepository.existsByLoginId(newLogInId))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);
    }

    private void checkNicknameDuplicate(String newNickname) {
        if (memberRepository.existsByNickname(newNickname))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
    }

    private void checkNicknameDuplicate(MemberEntity member, String newNickname) {
        if (newNickname != null && !member.getNickname().equals(newNickname) && memberRepository.existsByNickname(newNickname))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
    }
}
