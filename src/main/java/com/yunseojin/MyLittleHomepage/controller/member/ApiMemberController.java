package com.yunseojin.MyLittleHomepage.controller.member;

import com.yunseojin.MyLittleHomepage.application.auth.dto.command.LoginCommand;
import com.yunseojin.MyLittleHomepage.application.auth.dto.command.RefreshCommand;
import com.yunseojin.MyLittleHomepage.application.auth.dto.response.TokenResponse;
import com.yunseojin.MyLittleHomepage.application.contract.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.application.member.dto.command.CreateMemberCommand;
import com.yunseojin.MyLittleHomepage.application.member.dto.command.DeleteMemberCommand;
import com.yunseojin.MyLittleHomepage.application.member.dto.command.UpdateMemberCommand;
import com.yunseojin.MyLittleHomepage.application.member.dto.query.GetMemberQuery;
import com.yunseojin.MyLittleHomepage.application.member.dto.response.MemberResponse;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @PostMapping("/register")
    @ApiOperation(value = "회원가입", notes = "회원 정보를 생성합니다.")
    public ResponseEntity<MemberResponse> register(@RequestBody CreateMemberCommand command) {
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @PatchMapping("/modify")
    @ApiOperation(value = "정보 수정", notes = "회원 정보를 수정합니다.")
    public ResponseEntity<MemberResponse> modify(@RequestBody UpdateMemberCommand command) {
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 정보를 삭제합니다")
    public ResponseEntity<Void> delete(@RequestBody DeleteMemberCommand command) {
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "토큰을 사용해 로그인합니다.")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginCommand command) {
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "토큰갱신", notes = "토큰을 갱신합니다.")
    public ResponseEntity<String> refresh(
            HttpServletRequest request,
            @ApiIgnore RefreshCommand command) {

        var bearerToken = request.getHeader(refreshTokenName);
        command.setBearerToken(bearerToken);

        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(
            @ApiIgnore GetMemberQuery query) {

        return ResponseEntity.ok(applicationService.executeQuery(query));
    }
}