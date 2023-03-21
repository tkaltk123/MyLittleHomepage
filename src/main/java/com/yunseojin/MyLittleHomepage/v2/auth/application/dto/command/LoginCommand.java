package com.yunseojin.MyLittleHomepage.v2.auth.application.dto.command;


import com.yunseojin.MyLittleHomepage.v2.auth.application.dto.response.TokenResponse;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand implements Command<TokenResponse> {

    private String username;

    private String password;
}
