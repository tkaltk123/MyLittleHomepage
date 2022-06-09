package com.yunseojin.MyLittleHomepage.view.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.AuthProvider;

@Controller
@RequiredArgsConstructor
public class ViewController {
    @Resource
    private MemberInfo memberInfo;
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("login_id", memberInfo.getLoginId());
        model.addAttribute("nickname", memberInfo.getNickname());
        model.addAttribute("boards", boardService.getBoardList());
        return "index";
    }

    @GetMapping("/login")
    public String loginFrom(Model model) {
        model.addAttribute("memberRequest", new MemberRequest());
        return "layout/login";
    }

    @PostMapping(value = "/login")
    public String login(MemberRequest memberRequest) {
        memberService.login(memberRequest);
        return "redirect:/";
    }
    
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberRequest", new MemberRequest());
        return "layout/register";
    }

    @PostMapping(value = "/register")
    public String register( MemberRequest memberRequest) {
        memberService.register(memberRequest);
        return "redirect:/";
    }
    
    

}
