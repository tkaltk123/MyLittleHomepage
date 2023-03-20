package com.yunseojin.MyLittleHomepage.v2.member.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.member.domain.query.entity.QueriedMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberQuery implements Query<MemberResponse> {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private QueriedMember member;
}
