package com.yunseojin.MyLittleHomepage.v2.controller.member;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.util.CookieUtil;
import com.yunseojin.MyLittleHomepage.util.ModelUtil;
import com.yunseojin.MyLittleHomepage.v2.config.web.resolver.LoginUser;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberTokenDto;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.CreateMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.DeleteMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.UpdateMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@Controller
@ApiIgnore
public class MemberController {

    private final BoardService boardService;

    private final ApplicationService applicationService;

    @Value("${jwt.access.name}")
    private String accessTokenName;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Value("${jwt.refresh.expirationMilli}")
    private Integer refreshTokenRemainHour;


    @GetMapping("/login")
    public String loginFrom(
            @LoginUser QueriedMember member,
            Model model,
            HttpServletRequest request) {

        if (Objects.isNull(member)) {
            return "redirect:/";
        }

        model.addAttribute("command", new CreateMemberCommand());
        model.addAttribute("referer", request.getHeader("Referer"));

        return "layout/login";
    }

    @Login(required = false)
    @PostMapping("/login")
    public String login(
            CreateMemberCommand command,
            @RequestParam(required = false, name = "referer", defaultValue = "/") String referer,
            HttpServletResponse response) {

        var token = applicationService.executeCommand(command);
        response.addCookie(CookieUtil.createTokenCookie(accessTokenName, refreshTokenRemainHour,
                token.getAccessToken()));
        response.addCookie(CookieUtil.createTokenCookie(refreshTokenName, refreshTokenRemainHour,
                token.getRefreshToken()));

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
            @LoginUser QueriedMember member,
            Model model) {

        if (Objects.isNull(member)) {
            return "redirect:/";
        }

        model.addAttribute("command", new CreateMemberCommand());

        return "layout/register";
    }

    @Login(required = false)
    @PostMapping("/register")
    public String register(
            @Validated(ValidationGroups.Create.class) CreateMemberCommand command,
            HttpServletResponse response) {

        var token = applicationService.executeCommand(command);
        response.addCookie(CookieUtil.createTokenCookie(accessTokenName, refreshTokenRemainHour,
                token.getAccessToken()));
        response.addCookie(CookieUtil.createTokenCookie(refreshTokenName, refreshTokenRemainHour,
                token.getRefreshToken()));

        return "redirect:/";
    }

    @GetMapping("/modify")
    public String modifyForm(
            @LoginUser QueriedMember member,
            Model model) {

        if (Objects.isNull(member)) {
            return "redirect:/login";
        }

        var command = UpdateMemberCommand.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .build();

        model.addAttribute("command", command);
        ModelUtil.setCommonAttr(model,
                MemberTokenDto.builder()
                        .id(member.getId())
                        .loginId(member.getUsername())
                        .nickname(member.getNickname())
                        .build(),
                boardService.getBoardList());

        return "/layout/modifyForm";
    }

    @Login
    @PostMapping("/modify")
    public String modify(
            @LoginUser QueriedMember member,
            @Validated(ValidationGroups.Update.class) UpdateMemberCommand command,
            HttpServletResponse response) {

        command.setMemberId(member.getId());
        var token = applicationService.executeCommand(command);
        response.addCookie(
                CookieUtil.createTokenCookie(accessTokenName, refreshTokenRemainHour, token));

        return "redirect:/";
    }

    @Login
    @PostMapping("/delete")
    public String delete(
            @LoginUser QueriedMember member,
            DeleteMemberCommand command) {

        command.setMemberId(member.getId());
        applicationService.executeCommand(command);

        return "redirect:/";
    }
}
