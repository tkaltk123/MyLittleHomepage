package com.yunseojin.MyLittleHomepage.v2.member.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
public class GetMemberQuery implements Query<MemberResponse> {

    @JsonIgnore
    @Hidden
    @Setter
    private QueriedMember member;
}
