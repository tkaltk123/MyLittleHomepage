package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.mapper.MemberMapper;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.util.SessionUtil;
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
    public void resister(MemberRequest memberRequest) {
        SessionUtil.checkLogin(memberInfo, false);
        //로그인 ID 중복
        if (memberRepository.existsByLoginId(memberRequest.getLoginId()))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);
        //닉네임 중복
        if (memberRepository.existsByNickname(memberRequest.getNickname()))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
        //엔티티 생성
        var member = MemberMapper.INSTANCE.toMemberEntity(memberRequest);
        member.setMemberType(MemberType.NORMAL);
        memberRepository.save(member);
        memberInfo.setMember(member);
    }

    @Override
    public void modify(MemberRequest memberRequest) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        var loginId = memberRequest.getLoginId();
        var nickname = memberRequest.getNickname();
        var password = memberRequest.getPassword();
        checkLogInIdDuplicate(member, loginId);
        checkNicknameDuplicate(member, nickname);
        //정보 수정
        if (loginId != null) {
            member.setLoginId(loginId);
            memberInfo.setMember(member);
        }
        if (nickname != null)
            member.setNickname(nickname);
        if (password != null)
            member.setPassword(PasswordUtil.getHashedPassword(password));
    }

    @Override
    public void delete(MemberRequest memberRequest) {
        SessionUtil.checkLogin(memberInfo, true);
        var member = memberRepository.getMember(memberInfo.getId());
        PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword());
        memberRepository.delete(member);
        member.setIsDeleted(1);
        memberInfo.clear();
    }

    @Override
    public void login(MemberRequest memberRequest) {
        SessionUtil.checkLogin(memberInfo, false);
        var member = memberRepository.findByLoginId(memberRequest.getLoginId());
        //존재하지 않는 회원일 경우
        if (member == null)
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword());
        //세션에 저장
        memberInfo.setMember(member);
    }

    @Override
    public void logout() {
        SessionUtil.checkLogin(memberInfo, true);
        memberInfo.clear();
    }

    private void checkLogInIdDuplicate(MemberEntity member, String newLogInId) {
        if (newLogInId != null && !member.getLoginId().equals(newLogInId) && memberRepository.existsByLoginId(newLogInId))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);
    }

    private void checkNicknameDuplicate(MemberEntity member, String newNickname) {
        if (newNickname != null && !member.getNickname().equals(newNickname) && memberRepository.existsByNickname(newNickname))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
    }
}
