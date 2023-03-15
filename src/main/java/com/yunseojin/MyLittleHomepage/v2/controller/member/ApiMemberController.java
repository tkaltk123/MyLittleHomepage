package com.yunseojin.MyLittleHomepage.v2.controller.member;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.enums.ErrorMessage;
import com.yunseojin.MyLittleHomepage.etc.exception.BadRequestException;
import com.yunseojin.MyLittleHomepage.v2.auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.config.web.resolver.LoginUser;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.CreateMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.DeleteMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.LoginCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.RefreshCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.command.UpdateMemberCommand;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.query.GetMemberQuery;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class ApiMemberController {

    private final ApplicationService applicationService;

    @Value("${jwt.refresh.name}")
    private String refreshTokenName;

    @Login(required = false)
    @PostMapping("/register")
    @ApiOperation(value = "회원가입", notes = "회원 정보를 생성합니다.")
    public ResponseEntity<Token> register(@RequestBody CreateMemberCommand command) {

        return new ResponseEntity<>(applicationService.executeCommand(command), HttpStatus.OK);
    }

    @PatchMapping("/modify")
    @ApiOperation(value = "정보 수정", notes = "회원 정보를 수정합니다.")
    public ResponseEntity<String> modify(
            @ApiIgnore @LoginUser Member member,
            @RequestBody UpdateMemberCommand command) {
        command.setMember(member);
        return new ResponseEntity<>(applicationService.executeCommand(command), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 삭제합니다")
    public ResponseEntity<Void> delete(
            @ApiIgnore @LoginUser Member member,
            @RequestBody DeleteMemberCommand command) {

        command.setMember(member);

        return new ResponseEntity<>(applicationService.executeCommand(command), HttpStatus.OK);
    }

    @Login(required = false)
    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "토큰을 사용해 로그인합니다.")
    public ResponseEntity<Token> login(@RequestBody LoginCommand command) {

        return new ResponseEntity<>(applicationService.executeCommand(command), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "토큰갱신", notes = "토큰을 갱신합니다.")
    public ResponseEntity<String> refresh(HttpServletRequest request) {

        var refreshToken = getToken(request.getHeader(refreshTokenName));

        return new ResponseEntity<>(
                applicationService.executeCommand(new RefreshCommand(refreshToken)),
                HttpStatus.OK);
    }

    private String getToken(String bearerToken) {

        if (bearerToken == null) {
            return null;
        }

        if (!bearerToken.startsWith("Bearer ")) {
            throw new BadRequestException(ErrorMessage.INVALID_TOKEN_EXCEPTION);
        }

        return bearerToken.substring(7);

    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@ApiIgnore @LoginUser Member member) {
        GetMemberQuery query = new GetMemberQuery();
        query.setMember(member);
        return ResponseEntity.ok(applicationService.executeQuery(query));
    }
}
