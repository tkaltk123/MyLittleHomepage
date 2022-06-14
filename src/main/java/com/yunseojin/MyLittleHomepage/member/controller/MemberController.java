package com.yunseojin.MyLittleHomepage.member.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class MemberController {
    @Resource
    private MemberInfo memberInfo;
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginFrom(Model model, HttpServletRequest request) {

        if (memberInfo.getId() != null)
            return "redirect:/";

        model.addAttribute("memberRequest", new MemberRequest());
        model.addAttribute("referer", request.getHeader("Referer"));

        return "layout/login";
    }

    @PostMapping("/login")
    public String login(
            MemberRequest memberRequest,
            @RequestParam(required = false, name = "referer", defaultValue = "/") String referer) {

        memberService.login(memberRequest);
        return "redirect:" + referer;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        memberService.logout();
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        if (memberInfo.getId() != null)
            return "redirect:/";

        model.addAttribute("memberRequest", new MemberRequest());
        return "layout/register";
    }

    @PostMapping(value = "/register")
    public String register(
            @Validated(ValidationGroups.Register.class) MemberRequest memberRequest) {

        memberService.register(memberRequest);
        return "redirect:/";
    }
}
