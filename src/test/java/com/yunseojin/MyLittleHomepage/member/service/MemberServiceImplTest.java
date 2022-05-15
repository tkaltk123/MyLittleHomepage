package com.yunseojin.MyLittleHomepage.member.service;

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
        assertThrows(BadRequestException.class, () -> memberService.resister(req3));
        memberService.logout();
        assertThrows(BadRequestException.class, () -> memberService.resister(req));
        assertThrows(BadRequestException.class, () -> memberService.resister(req2));
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

        assertThrows(BadRequestException.class, () -> memberService.modify(modify2));
        assertThrows(BadRequestException.class, () -> memberService.modify(modify3));
        memberService.logout();
        assertThrows(BadRequestException.class, () -> memberService.modify(resist1));
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
        assertThrows(BadRequestException.class, () -> memberService.delete(resist));
        memberService.login(resist);
        assertThrows(BadRequestException.class, () -> memberService.delete(delete2));
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
        assertThrows(BadRequestException.class, () -> memberService.login(req2));
        assertThrows(BadRequestException.class, () -> memberService.login(req3));
        memberService.login(req);
        assertThrows(BadRequestException.class, () -> memberService.login(req));
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
        assertThrows(BadRequestException.class, () -> memberService.logout());
    }
}