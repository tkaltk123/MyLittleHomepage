package com.yunseojin.MyLittleHomepage.application.member.dto.query;

import com.yunseojin.MyLittleHomepage.application.member.dto.response.MemberResponse;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Query;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.MemberContainer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetMemberQuery extends MemberContainer implements Query<MemberResponse> {

}
