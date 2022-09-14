package com.yunseojin.MyLittleHomepage.member.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.annotation.MemberToken;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import com.yunseojin.MyLittleHomepage.util.CookieUtil;
import com.yunseojin.MyLittleHomepage.util.ModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final BoardService boardService;
    private final MemberService memberService;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Value("${jwt.refresh.remain}")
    private Integer refreshTokenRemainHour;


    @GetMapping("/login")
    public String loginFrom(
            @MemberToken MemberTokenDto memberTokenDto,
            Model model,
            HttpServletRequest request) {

        if (isLoggedIn(memberTokenDto))
            return "redirect:/";

        model.addAttribute("memberRequest", new MemberRequest());
        model.addAttribute("referer", request.getHeader("Referer"));

        return "layout/login";
    }

    @Login(required = false)
    @PostMapping("/login")
    public String login(
            MemberRequest memberRequest,
            @RequestParam(required = false, name = "referer", defaultValue = "/") String referer,
            HttpServletResponse response) {

        var token = memberService.login(memberRequest);
        response.addCookie(CookieUtil.createTokenCookie(accessTokenName, refreshTokenRemainHour, token.getAccessToken()));
        response.addCookie(CookieUtil.createTokenCookie(refreshTokenName, refreshTokenRemainHour, token.getRefreshToken()));

        return "redirect:" + referer;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        response.addCookie(new Cookie(accessTokenName, null));
        response.addCookie(new Cookie(refreshTokenName, null));

        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/register")
    public String registerForm(
            @MemberToken MemberTokenDto memberTokenDto,
            Model model) {

        if (isLoggedIn(memberTokenDto))
            return "redirect:/";

        model.addAttribute("memberRequest", new MemberRequest());

        return "layout/register";
    }

    @Login(required = false)
    @PostMapping("/register")
    public String register(
            @Validated(ValidationGroups.Create.class) MemberRequest memberRequest,
            HttpServletResponse response) {

        var token = memberService.register(memberRequest);
        response.addCookie(CookieUtil.createTokenCookie(accessTokenName, refreshTokenRemainHour, token.getAccessToken()));
        response.addCookie(CookieUtil.createTokenCookie(refreshTokenName, refreshTokenRemainHour, token.getRefreshToken()));

        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modifyForm(
            @MemberToken MemberTokenDto memberTokenDto,
            Model model) {

        if (!isLoggedIn(memberTokenDto))
            return "redirect:/login";

        var memberRequest = MemberRequest.builder()
                .loginId(memberTokenDto.getLoginId())
                .nickname(memberTokenDto.getNickname())
                .build();

        model.addAttribute("memberRequest", memberRequest);
        ModelUtil.setCommonAttr(model, memberTokenDto, boardService.getBoardList());

        return "/layout/modifyForm";
    }

    @Login
    @PostMapping("/modify")
    public String modify(
            @MemberToken MemberTokenDto memberTokenDto,
            @Validated(ValidationGroups.Update.class) MemberRequest memberRequest,
            HttpServletResponse response) {

        var token = memberService.modify(memberTokenDto.getId(), memberRequest);
        response.addCookie(CookieUtil.createTokenCookie(accessTokenName, refreshTokenRemainHour, token));

        return "redirect:/";
    }

    @Login
    @PostMapping("/delete")
    public String delete(
            @MemberToken MemberTokenDto memberTokenDto,
            MemberRequest memberRequest) {

        memberService.delete(memberTokenDto.getId(), memberRequest);

        return "redirect:/";
    }

    private boolean isLoggedIn(MemberTokenDto memberTokenDto) {

        return memberTokenDto != null && memberTokenDto.getId() != null;
    }
}
