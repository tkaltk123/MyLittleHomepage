package com.yunseojin.MyLittleHomepage.v2.application.auth.mapper;

import com.yunseojin.MyLittleHomepage.v2.application.auth.dto.response.TokenResponse;
import com.yunseojin.MyLittleHomepage.v2.domain.auth.vo.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenResponse toResponse(Token token);
}
