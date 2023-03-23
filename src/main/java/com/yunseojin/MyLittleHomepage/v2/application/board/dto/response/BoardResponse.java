package com.yunseojin.MyLittleHomepage.v2.application.board.dto.response;

import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BoardResponse extends BaseResponse {

    private String name;

    private BoardCountResponse boardCount;
}
