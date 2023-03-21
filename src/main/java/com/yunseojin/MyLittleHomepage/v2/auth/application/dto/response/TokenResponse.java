package com.yunseojin.MyLittleHomepage.v2.auth.application.dto.response;

import lombok.Data;

@Data
public class TokenResponse {

    private final String accessToken;

    private final String refreshToken;
}
