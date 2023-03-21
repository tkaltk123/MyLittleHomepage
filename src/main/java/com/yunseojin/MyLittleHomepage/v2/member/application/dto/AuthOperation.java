package com.yunseojin.MyLittleHomepage.v2.member.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuthOperation {

    @JsonIgnore
    protected QueriedMember member;

    public Long getMemberId() {
        if (Objects.isNull(member)) {
            return null;
        }
        return member.getId();
    }
}
