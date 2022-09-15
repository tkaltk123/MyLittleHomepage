package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.enums.TokenValidationType;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.etc.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import javax.transaction.Transactional;

import static com.yunseojin.MyLittleHomepage.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class MemberServiceImplTest {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtService jwtUtil;

    private MemberEntity member;

    @BeforeEach
    void init() {

        member = memberRepository.save(createTestMember("testUser", "testUser"));
    }

    @Test
    void register() {

        //로그인 id가 중복된 경우
        assertError(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION, () ->
                memberService.register(createMemberRequest("testUser", "testUser2", null))
        );

        //닉네임이 중복된 경우
        assertError(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION, () ->
                memberService.register(createMemberRequest("testUser2", "testUser", null))
        );

        //회원가입
        memberService.register(createMemberRequest("testUser2", "testUser2", null));
        var dbMember = memberRepository.findByLoginId("testUser2").get();

        // then
        assertEquals("testUser2", dbMember.getNickname());
    }

    @Test
    void modify() {

        //회원 가입
        memberService.register(createMemberRequest("testUser2", "testUser2", null));
        var dbMember = memberRepository.findByLoginId("testUser2").get();

        //현재 패스워드가 잘못된 경우
        assertError(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION, () ->
                memberService.modify(dbMember.getId(),
                        createMemberRequest("testUser3", "testUser3", "12345"))
        );

        //로그인 ID가 중복된 경우
        assertError(ErrorMessage.LOGIN_ID_DUPLICATE_EXCEPTION, () ->
                memberService.modify(dbMember.getId(),
                        createMemberRequest("testUser", "testUser3", "1234"))
        );

        //닉네임이 중복된 경우
        assertError(ErrorMessage.NICKNAME_DUPLICATE_EXCEPTION, () ->
                memberService.modify(dbMember.getId(),
                        createMemberRequest("testUser3", "testUser", "1234"))
        );

        //회원정보 수정
        var token = memberService.modify(dbMember.getId(),
                createMemberRequest("testUser3", "testUser3", "1234"));

        // then
        assertEquals("testUser3", dbMember.getNickname());
        assertEquals(TokenValidationType.VALID, jwtUtil.validateAccessToken(token));
    }

    @Test
    void delete() {

        //현재 패스워드가 잘못된 경우
        assertError(ErrorMessage.INCORRECT_PASSWORD_EXCEPTION, () ->
                memberService.delete(member.getId(),
                        createMemberRequest(null, null, "12345"))
        );

        //제거
        memberService.delete(member.getId(),
                createMemberRequest(null, null, "1234"));

        // then
        assertEquals(1, member.getIsDeleted());
    }

    @Test
    void login() {

        //login id가 잘못된 경우
        assertError(ErrorMessage.LOGIN_FAILED_EXCEPTION, () ->
                memberService.login(createMemberRequest("testUser2", null, null))
        );

        //비밀번호가 잘못된 경우
        var loginRequest = MemberRequest.builder()
                .loginId("testUser")
                .password("123")
                .build();
        assertError(ErrorMessage.LOGIN_FAILED_EXCEPTION, () ->
                memberService.login(loginRequest)
        );

        //로그인
        var token = memberService.login(createMemberRequest("testUser", null, null));

        //then
        assertEquals(TokenValidationType.VALID, jwtUtil.validateAccessToken(token.getAccessToken()));
    }
}