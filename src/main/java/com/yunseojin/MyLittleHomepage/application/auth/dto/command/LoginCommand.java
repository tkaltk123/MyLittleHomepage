package com.yunseojin.MyLittleHomepage.application.auth.dto.command;


import com.yunseojin.MyLittleHomepage.application.auth.dto.response.TokenResponse;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;
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
