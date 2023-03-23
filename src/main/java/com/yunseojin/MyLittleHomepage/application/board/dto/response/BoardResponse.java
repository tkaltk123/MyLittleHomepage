package com.yunseojin.MyLittleHomepage.application.board.dto.response;

import com.yunseojin.MyLittleHomepage.application.contract.dto.BaseResponse;
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
