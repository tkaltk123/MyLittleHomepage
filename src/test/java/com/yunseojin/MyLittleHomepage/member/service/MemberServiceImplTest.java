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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceImpl memberService;

    @Test
    void resister() {
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
        memberService.resister(req);
        var dbMember = memberRepository.findByLoginId(req.getLoginId());
        // then
        assertEquals(req.getNickname(), dbMember.getNickname());
        assertTrue(PasswordUtil.checkPassword(req.getPassword(), dbMember.getPassword()));
        assertEquals(MemberType.NORMAL, dbMember.getMemberType());

        //로그인 x
        assertEquals(ErrorMessage.ALREADY_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.resister(req3)).getCode());
        memberService.logout();
        //id 중복
        assertEquals(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.resister(req)).getCode());
        //닉네임 중복
        assertEquals(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.resister(req2)).getCode());
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
        memberService.resister(resist1);
        memberService.logout();
        memberService.resister(resist2);
        memberService.logout();
        memberService.login(resist1);
        memberService.modify(modify1);
        var dbMember = memberRepository.findByLoginId(modify1.getLoginId());
        // then
        assertEquals(modify1.getLoginId(), dbMember.getLoginId());
        assertEquals(modify1.getNickname(), dbMember.getNickname());
        assertTrue(PasswordUtil.checkPassword(modify1.getPassword(), dbMember.getPassword()));
        //닉네임 중복
        assertEquals(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.modify(modify2)).getCode());
        //id 중복
        assertEquals(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.modify(modify3)).getCode());
        memberService.logout();
        //로그인 x
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.modify(resist1)).getCode());
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
        memberService.resister(resist);
        // then
        memberService.logout();
        //로그인 x
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.delete(resist)).getCode());
        memberService.login(resist);
        //pw 오류
        assertEquals(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.delete(delete2)).getCode());
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
        memberService.resister(req);
        memberService.logout();
        //then
        //id 오류
        assertEquals(ErrorMessage.NOT_EXISTS_MEMBER_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.login(req2)).getCode());
        //pw 오류
        assertEquals(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.login(req3)).getCode());
        memberService.login(req);
        //중복 로그인
        assertEquals(ErrorMessage.ALREADY_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.login(req)).getCode());
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
        memberService.resister(req);
        memberService.logout();
        //then
        //중복 로그아웃
        assertEquals(ErrorMessage.NOT_LOGIN_EXCEPTION.getCode(),
                assertThrows(BadRequestException.class,
                        () -> memberService.logout()).getCode());
    }
}