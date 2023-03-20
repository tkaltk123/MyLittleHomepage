package com.yunseojin.MyLittleHomepage.v2.member.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuthOperation {

    @JsonIgnore
    protected Long memberId;
}
