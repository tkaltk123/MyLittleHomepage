package com.yunseojin.MyLittleHomepage.v2.board.application.dto.response;

import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.BaseResponse;
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
