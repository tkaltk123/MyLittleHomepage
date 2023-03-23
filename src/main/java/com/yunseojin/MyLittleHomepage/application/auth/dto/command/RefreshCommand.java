package com.yunseojin.MyLittleHomepage.application.auth.dto.command;

import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshCommand implements Command<String> {

    private String bearerToken;
}
