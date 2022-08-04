package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.yunseojin.MyLittleHomepage.TestUtil.assertError;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceImpl memberService;

    private static MemberRequest memberRequest;

    private MemberEntity member;

    @BeforeAll
    static void setup() {

        memberRequest = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname")
                .build();
    }

    @BeforeEach
    void init() {

        memberService.register(memberRequest);
        member = memberRepository.findByLoginId(memberRequest.getLoginId()).get();
        memberService.logout();
    }

    @Test
    void register() {

        //이미 로그인 된 경우
        memberService.login(memberRequest);
        assertError(ErrorMessage.ALREADY_LOGIN_EXCEPTION, () ->
                memberService.register(memberRequest)
        );
        memberService.logout();

        //로그인 id가 중복된 경우
        var memberRequest2 = memberRequest.toBuilder()
                .nickname("nickname2")
                .build();
        assertError(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION, () ->
                memberService.register(memberRequest2)
        );

        //닉네임이 중복된 경우
        var memberRequest3 = memberRequest.toBuilder()
                .loginId("login_id2")
                .build();
        assertError(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION, () ->
                memberService.register(memberRequest3)
        );

        //회원가입
        var memberRequest4 = memberRequest.toBuilder()
                .loginId("login_id2")
                .nickname("nickname2")
                .build();
        memberService.register(memberRequest4);
        var dbMember = memberRepository.findByLoginId(memberRequest4.getLoginId()).get();

        // then
        assertEquals(memberRequest4.getNickname(), dbMember.getNickname());
    }

    @Test
    void modify() {

        //로그인이 안 된 경우
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                memberService.modify(memberRequest)
        );

        //회원 가입
        var registerRequest = memberRequest.toBuilder()
                .loginId("login_id2")
                .nickname("nickname2")
                .build();
        memberService.register(registerRequest);
        var dbMember = memberRepository.findByLoginId(registerRequest.getLoginId()).get();

        //현재 패스워드가 잘못된 경우
        assertError(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION, () ->
                memberService.modify(registerRequest)
        );

        //로그인 id가 중복된 경우
        var modifyRequest = registerRequest.toBuilder()
                .loginId("login_id")
                .currentPassword(registerRequest.getPassword())
                .build();
        assertError(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION, () ->
                memberService.modify(modifyRequest)
        );

        //닉네임이 중복된 경우
        var modifyRequest2 = registerRequest.toBuilder()
                .nickname("nickname")
                .currentPassword(registerRequest.getPassword())
                .build();
        assertError(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION, () ->
                memberService.modify(modifyRequest2)
        );

        //회원정보 수정
        var modifyRequest3 = registerRequest.toBuilder()
                .loginId("login_id3")
                .nickname("nickname3")
                .currentPassword(registerRequest.getPassword())
                .build();
        memberService.modify(modifyRequest3);

        // then
        assertEquals(modifyRequest3.getNickname(), dbMember.getNickname());
    }

    @Test
    void delete() {

        //로그인이 안 된 경우
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                memberService.delete(memberRequest)
        );

        //현재 패스워드가 잘못된 경우
        memberService.login(memberRequest);
        assertError(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION, () ->
                memberService.delete(memberRequest)
        );

        //제거
        var deleteRequest = MemberRequest.builder()
                .currentPassword(memberRequest.getPassword())
                .build();
        memberService.delete(deleteRequest);

        // then
        assertEquals(1, member.getIsDeleted());
    }

    @Test
    void login() {

        //login id가 잘못된 경우
        var loginRequest = memberRequest.toBuilder()
                .loginId("login")
                .build();
        assertError(ErrorMessage.LOGIN_FAILED_EXCEPTION, () ->
                memberService.login(loginRequest)
        );

        //비밀번호가 잘못된 경우
        var loginRequest2 = memberRequest.toBuilder()
                .password("123")
                .build();
        assertError(ErrorMessage.LOGIN_FAILED_EXCEPTION, () ->
                memberService.login(loginRequest2)
        );

        //로그인
        memberService.login(memberRequest);

        //중복 로그인
        assertError(ErrorMessage.ALREADY_LOGIN_EXCEPTION, () ->
                memberService.login(memberRequest)
        );

    }

    @Test
    void logout() {

        //로그인이 안 되있을 경우
        assertError(ErrorMessage.NOT_LOGIN_EXCEPTION, () ->
                memberService.logout()
        );

        //로그인
        memberService.login(memberRequest);

        //로그아웃
        memberService.logout();

    }
}