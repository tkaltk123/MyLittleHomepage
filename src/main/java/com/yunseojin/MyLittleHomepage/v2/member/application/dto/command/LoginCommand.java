package com.yunseojin.MyLittleHomepage.v2.member.application.dto.command;


import auth.dto.Token;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand implements Command<Token> {

    private String username;

    private String password;
}
