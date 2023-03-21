package com.yunseojin.MyLittleHomepage.v2.member.application.dto.query;

import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.response.MemberResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetMemberQuery extends AuthOperation implements Query<MemberResponse> {

}
