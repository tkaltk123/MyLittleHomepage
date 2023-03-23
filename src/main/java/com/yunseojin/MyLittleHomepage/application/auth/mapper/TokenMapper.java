package com.yunseojin.MyLittleHomepage.application.auth.mapper;

import com.yunseojin.MyLittleHomepage.domain.auth.vo.Token;
import com.yunseojin.MyLittleHomepage.application.auth.dto.response.TokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenResponse toResponse(Token token);
}
