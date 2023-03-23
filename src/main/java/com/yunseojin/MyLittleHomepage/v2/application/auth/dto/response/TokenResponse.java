package com.yunseojin.MyLittleHomepage.v2.application.auth.dto.response;

import lombok.Data;

@Data
public class TokenResponse {

    private final String accessToken;

    private final String refreshToken;
}
