package com.yunseojin.MyLittleHomepage.member.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/modify")
    public ResponseEntity<?> modify(
            @RequestBody @Validated(ValidationGroups.Update.class) MemberRequest memberRequest) {
        memberService.modify(memberRequest);
        return new ResponseEntity<>("회원정보를 수정했습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestBody @Validated(ValidationGroups.Delete.class) MemberRequest memberRequest) {
        memberService.delete(memberRequest);
        return new ResponseEntity<>("회원 탈퇴를 성공했습니다.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Validated(ValidationGroups.LogIn.class) MemberRequest memberRequest) {
        memberService.login(memberRequest);
        return new ResponseEntity<>("로그인이 성공했습니다.", HttpStatus.OK);
    }

    @GetMapping("/log_out")
    public ResponseEntity<?> logout() {
        memberService.logout();
        return new ResponseEntity<>("로그아웃이 성공했습니다.", HttpStatus.OK);
    }
}
