package com.yunseojin.MyLittleHomepage.v2.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {

    private final String accessToken;

    private final String refreshToken;
}
