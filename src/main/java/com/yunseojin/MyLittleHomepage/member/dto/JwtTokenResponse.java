package com.yunseojin.MyLittleHomepage.member.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtTokenResponse {

    String accessToken;
    String refreshToken;
}
