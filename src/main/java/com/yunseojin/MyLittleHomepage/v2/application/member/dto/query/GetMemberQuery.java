package com.yunseojin.MyLittleHomepage.v2.application.member.dto.query;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.MemberContainer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetMemberQuery extends MemberContainer implements Query<MemberResponse> {

}
