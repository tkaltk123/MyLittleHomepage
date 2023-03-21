package com.yunseojin.MyLittleHomepage.v2.auth.application.mapper;

import com.yunseojin.MyLittleHomepage.v2.auth.application.dto.response.TokenResponse;
import com.yunseojin.MyLittleHomepage.v2.auth.domain.model.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenResponse toResponse(Token token);
}
