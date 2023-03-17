package com.yunseojin.MyLittleHomepage.v2.member.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class AuthOperation {

    @Hidden
    @JsonIgnore
    protected Long memberId;
}
