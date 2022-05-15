package com.yunseojin.MyLittleHomepage.member.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/resister")
    public ResponseEntity<?> resister(
            @RequestBody @Validated(ValidationGroups.SignUp.class) MemberRequest memberRequest) {
        memberService.resister(memberRequest);
        return new ResponseEntity<>("회원가입이 성공했습니다.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Validated(ValidationGroups.LogIn.class) MemberRequest memberRequest) {
        memberService.login(memberRequest);
        return new ResponseEntity<>("로그인이 성공했습니다.", HttpStatus.OK);
    }
}
