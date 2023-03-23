package com.yunseojin.MyLittleHomepage.domain.auth.vo;

import lombok.Data;

@Data
public class Token {

    private final String accessToken;

    private final String refreshToken;
}
