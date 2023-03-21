package com.yunseojin.MyLittleHomepage.v2.auth.application.dto.command;

import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshCommand implements Command<String> {

    private String refreshToken;
}
