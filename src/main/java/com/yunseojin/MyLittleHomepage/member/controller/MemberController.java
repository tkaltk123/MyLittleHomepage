package com.yunseojin.MyLittleHomepage.member.controller;

import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Boolean> resister(@RequestBody MemberRequest memberRequest) {
        return new ResponseEntity<>(memberService.resister(memberRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody MemberRequest memberRequest) {
        return new ResponseEntity<>(memberService.login(memberRequest), HttpStatus.OK);
    }

    @GetMapping("/session")
    public ResponseEntity<String> get() {
        return new ResponseEntity<>(memberService.get(), HttpStatus.OK);
    }
}
