package com.yunseojin.MyLittleHomepage.domain.member.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class MemberContainer {

    @JsonIgnore
    protected QueriedMember member;

    public Long getMemberId() {
        if (Objects.isNull(member)) {
            return null;
        }
        return member.getId();
    }
}
