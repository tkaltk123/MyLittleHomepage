package com.yunseojin.MyLittleHomepage.member.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    @ApiOperation(value = "회원가입", notes = "회원 정보를 생성합니다.")
    public ResponseEntity<?> register(
            @RequestBody @Validated(ValidationGroups.Register.class) MemberRequest memberRequest) {
        return new ResponseEntity<>(memberService.register(memberRequest), HttpStatus.OK);
    }

    @PatchMapping("/modify")
    @ApiOperation(value = "정보 수정", notes = "회원 정보를 수정합니다.")
    public ResponseEntity<?> modify(
            @RequestBody @Validated(ValidationGroups.Update.class) MemberRequest memberRequest) {
        return new ResponseEntity<>(memberService.modify(memberRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 삭제합니다")
    public ResponseEntity<?> delete(
            @RequestBody @Validated(ValidationGroups.Delete.class) MemberRequest memberRequest) {
        memberService.delete(memberRequest);
        return new ResponseEntity<>("회원 탈퇴를 성공했습니다.", HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "세션을 사용해 로그인합니다.")
    public ResponseEntity<?> login(
            @RequestBody @Validated(ValidationGroups.LogIn.class) MemberRequest memberRequest) {
        return new ResponseEntity<>(memberService.login(memberRequest), HttpStatus.OK);
    }

    @GetMapping("/log_out")
    @ApiOperation(value = "로그아웃", notes = "로그아웃")
    public ResponseEntity<?> logout() {
        memberService.logout();
        return new ResponseEntity<>("로그아웃이 성공했습니다.", HttpStatus.OK);
    }
}
