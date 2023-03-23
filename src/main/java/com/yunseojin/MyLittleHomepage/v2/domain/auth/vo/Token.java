package com.yunseojin.MyLittleHomepage.v2.domain.auth.vo;

import lombok.Data;

@Data
public class Token {

    private final String accessToken;

    private final String refreshToken;
}
