package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
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
        //로그인이 안되있을 경우
        if (memberInfo.getId() == null)
            throw new BadRequestException(ErrorMessage.NOT_LOGIN_EXCEPTION);
        var _member = memberRepository.findById(memberInfo.getId());
        //존재하지 않는 회원일 경우
        if (_member.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        var member = _member.get();
        var loginId = memberRequest.getLoginId();
        var nickname = memberRequest.getNickname();
        var password = memberRequest.getPassword();
        //로그인 ID가 중복될 경우
        if (loginId != null && !member.getLoginId().equals(loginId) && memberRepository.existsByLoginId(loginId))
            throw new BadRequestException(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION);
        //닉네임이 중복될 경우
        if (nickname != null && !member.getNickname().equals(nickname) && memberRepository.existsByNickname(nickname))
            throw new BadRequestException(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION);
        //정보 수정
        if (loginId != null) {
            member.setLoginId(loginId);
            memberInfo.setLoginId(loginId);
        }
        if (nickname != null)
            member.setNickname(nickname);
        if (password != null)
            member.setPassword(PasswordUtil.getHashedPassword(password));
    }

    @Override
    public void delete(MemberRequest memberRequest) {
        //로그인이 안되있을 경우
        if (memberInfo.getId() == null)
            throw new BadRequestException(ErrorMessage.NOT_LOGIN_EXCEPTION);
        var _member = memberRepository.findById(memberInfo.getId());
        //존재하지 않는 회원일 경우
        if (_member.isEmpty())
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        var member = _member.get();
        if (!PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword()))
            throw new BadRequestException(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION);
        memberRepository.delete(member);
    }

    @Override
    public void login(MemberRequest memberRequest) {
        //이미 로그인 되어있을 경우
        if (memberInfo.getId() != null)
            throw new BadRequestException(ErrorMessage.ALREADY_LOGIN_EXCEPTION);
        var member = memberRepository.findByLoginId(memberRequest.getLoginId());
        //존재하지 않는 회원일 경우
        if (member == null)
            throw new BadRequestException(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION);
        //비밀번호가 일치하지 않을 경우
        if (!PasswordUtil.checkPassword(memberRequest.getPassword(), member.getPassword()))
            throw new BadRequestException(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION);
        //세션에 저장
        memberInfo.setId(member.getId());
        memberInfo.setLoginId(member.getLoginId());
    }

    @Override
    public void logout() {
        //로그인이 안되있을 경우
        if (memberInfo.getId() == null)
            throw new BadRequestException(ErrorMessage.NOT_LOGIN_EXCEPTION);
        //세션 제거
        memberInfo.setId(null);
        memberInfo.setLoginId(null);
    }

}
