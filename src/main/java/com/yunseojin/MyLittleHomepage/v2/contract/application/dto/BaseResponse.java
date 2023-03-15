package com.yunseojin.MyLittleHomepage.v2.contract.application.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseResponse {

    protected Long id;

    protected Date createdAt;

    protected Date updatedAt;
}
