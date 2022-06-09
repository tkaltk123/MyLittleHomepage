package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.yunseojin.MyLittleHomepage.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceImpl memberService;

    @Test
    void register() {
        //given
        var req = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname")
                .build();
        var req2 = MemberRequest.builder()
                .loginId("login_id2")
                .password("1234")
                .nickname("nickname")
                .build();
        var req3 = MemberRequest.builder()
                .loginId("login_id2")
                .password("1234")
                .nickname("nickname2")
                .build();
        //when
        memberService.register(req);
        var dbMember = memberRepository.findByLoginId(req.getLoginId());
        // then
        assertEquals(req.getNickname(), dbMember.getNickname());
        PasswordUtil.checkPassword(req.getPassword(), dbMember.getPassword());
        assertEquals(MemberType.NORMAL, dbMember.getMemberType());

        //로그인 x
        assertError(ErrorMessage.ALREADY_LOGIN_EXCEPTION, () ->
                memberService.register(req3)
        );
        memberService.logout();
        //id 중복
        assertError(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION, () ->
                memberService.register(req)
        );
        //닉네임 중복
        assertError(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION, () ->
                memberService.register(req2)
        );
    }

    @Test
    void modify() {
        //given
        var resist1 = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname")
                .build();
        var resist2 = MemberRequest.builder()
                .loginId("login_id2")
                .password("12345")
                .nickname("nickname2")
                .build();
        var modify1 = MemberRequest.builder()
                .loginId("login_id3")
                .password("12345")
                .nickname("nickname3")
                .build();
        var modify2 = MemberRequest.builder()
                .loginId("login_id2")
                .password("1234")
                .nickname("nickname")
                .build();
        var modify3 = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname2")
                .build();
        //when
        memberService.register(resist1);
        memberService.logout();
        memberService.register(resist2);
        memberService.logout();
        memberService.login(resist1);
        memberService.modify(modify1);
        var dbMember = memberRepository.findByLoginId(modify1.getLoginId());
        // then
        assertEquals(modify1.getLoginId(), dbMember.getLoginId());
        assertEquals(modify1.getNickname(), dbMember.getNickname());
        PasswordUtil.checkPassword(modify1.getPassword(), dbMember.getPassword());
        //닉네임 중복
        assertError(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION, () ->
                memberService.modify(modify2)
        );
        //id 중복
        assertError(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION, () ->
                memberService.modify(modify3)
        );
        memberService.logout();
        //로그인 x
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                memberService.modify(resist1)
        );
    }

    @Test
    void delete() {
        //given
        var resist = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname")
                .build();
        var delete2 = MemberRequest.builder()
                .loginId("login_id")
                .password("12345")
                .nickname("nickname")
                .build();
        //when
        memberService.register(resist);
        // then
        memberService.logout();
        //로그인 x
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                memberService.delete(resist)
        );
        memberService.login(resist);
        //pw 오류
        assertError(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION, () ->
                memberService.delete(delete2)
        );
        memberService.delete(resist);
        assertNull(memberRepository.findByLoginId(resist.getLoginId()));
    }

    @Test
    void login() {
        //given
        var req = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname")
                .build();
        var req2 = MemberRequest.builder()
                .loginId("login_id2")
                .password("1234")
                .nickname("nickname")
                .build();
        var req3 = MemberRequest.builder()
                .loginId("login_id")
                .password("12345")
                .nickname("nickname")
                .build();
        //when
        memberService.register(req);
        memberService.logout();
        //then
        //id 오류
        assertError(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION, () ->
                memberService.login(req2)
        );
        //pw 오류
        assertError(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION, () ->
                memberService.login(req3)
        );
        memberService.login(req);
        //중복 로그인
        assertError(ErrorMessage.ALREADY_LOGIN_EXCEPTION, () ->
                memberService.login(req)
        );
    }

    @Test
    void logout() {
        //given
        var req = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname")
                .build();
        //when
        memberService.register(req);
        memberService.logout();
        //then
        //중복 로그아웃
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                memberService.logout()
        );
    }
}