package com.yunseojin.MyLittleHomepage.view.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
@RequiredArgsConstructor
public class ViewController {
    @Resource
    private MemberInfo memberInfo;
    private final BoardService boardService;
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("")
    public String index(Model model) {
        setCommonData(model);
        return "index";
    }

    @GetMapping("/login")
    public String loginFrom(Model model, HttpServletRequest request) {
        if (memberInfo.getId() != null)
            return "redirect:/";
        model.addAttribute("memberRequest", new MemberRequest());
        model.addAttribute("referer", request.getHeader("Referer"));
        return "layout/login";
    }

    @PostMapping(value = "/login")
    public String login(
            @RequestParam(required = false, name = "referer", defaultValue = "/") String referer,
            MemberRequest memberRequest) {
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
        model.addAttribute("memberRequest", new MemberRequest());
        return "layout/register";
    }

    @PostMapping(value = "/register")
    public String register(@Validated(ValidationGroups.Register.class) MemberRequest memberRequest) {
        memberService.register(memberRequest);
        return "redirect:/";
    }

    @GetMapping("/boards/{board_id}")
    public String board(
            @PathVariable(name = "board_id") Long boardId,
            @RequestParam(required = false, name = "page", defaultValue = "0") Integer page,
            Model model) {

        var postPage = postService.getPostList(boardId, page);
        var currenPage = postPage.getNumber();
        var startPage = currenPage - currenPage % 5;
        var endPage = Math.max(0, Math.min(startPage + 4, postPage.getTotalPages() - 1));

        setCommonData(model);
        model.addAttribute("boardId", boardId);
        model.addAttribute("currentPage", currenPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", postPage.getTotalPages());
        model.addAttribute("posts", postPage.getContent());
        return "layout/board";
    }

    private void setCommonData(Model model) {
        model.addAttribute("login_id", memberInfo.getLoginId());
        model.addAttribute("nickname", memberInfo.getNickname());
        model.addAttribute("boards", boardService.getBoardList());
    }

}
