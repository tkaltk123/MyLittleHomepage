package com.yunseojin.MyLittleHomepage.application.auth.dto.response;

import lombok.Data;

@Data
public class TokenResponse {

    private final String accessToken;

    private final String refreshToken;
}
