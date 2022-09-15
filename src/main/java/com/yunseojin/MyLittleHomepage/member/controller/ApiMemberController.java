package com.yunseojin.MyLittleHomepage.member.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.*;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class ApiMemberController {

    private final MemberService memberService;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Login(required = false)
    @PostMapping("/register")
    @ApiOperation(value = "회원가입", notes = "회원 정보를 생성합니다.")
    public ResponseEntity<?> register(
            @RequestBody @Validated(ValidationGroups.Create.class) MemberRequest memberRequest) {

        return new ResponseEntity<>(memberService.register(memberRequest), HttpStatus.OK);
    }

    @Login
    @PatchMapping("/modify")
    @ApiOperation(value = "정보 수정", notes = "회원 정보를 수정합니다.")
    public ResponseEntity<?> modify(
            @MemberId Long memberId,
            @RequestBody @Validated(ValidationGroups.Update.class) MemberRequest memberRequest) {

        return new ResponseEntity<>(memberService.modify(memberId, memberRequest), HttpStatus.OK);
    }

    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 삭제합니다")
    public ResponseEntity<?> delete(
            @MemberId Long memberId,
            @RequestBody @Validated(ValidationGroups.Delete.class) MemberRequest memberRequest) {

        memberService.delete(memberId, memberRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Login(required = false)
    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "토큰을 사용해 로그인합니다.")
    public ResponseEntity<?> login(@RequestBody MemberRequest memberRequest) {

        return new ResponseEntity<>(memberService.login(memberRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "토큰갱신", notes = "토큰을 갱신합니다.")
    public ResponseEntity<?> refresh(HttpServletRequest request) {

        var accessToken = getToken(request.getHeader(accessTokenName));
        var refreshToken = getToken(request.getHeader(refreshTokenName));

        return new ResponseEntity<>(memberService.refreshAccessToken(accessToken, refreshToken), HttpStatus.OK);
    }

    private String getToken(String bearerToken) {

        if (bearerToken == null)
            return null;

        if (!bearerToken.startsWith("Bearer "))
            throw new BadRequestException(ErrorMessage.INVALID_TOKEN_EXCEPTION);

        return bearerToken.substring(7);

    }
}
