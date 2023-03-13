package com.yunseojin.MyLittleHomepage.v2.member.application.command.refresh;

import com.yunseojin.MyLittleHomepage.v2.contract.application.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshCommand implements Command<String> {

    private String refreshToken;
}
