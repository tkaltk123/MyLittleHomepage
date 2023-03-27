package com.yunseojin.MyLittleHomepage.application.contract.dto;

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