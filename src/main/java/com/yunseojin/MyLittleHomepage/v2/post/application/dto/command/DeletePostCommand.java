package com.yunseojin.MyLittleHomepage.v2.post.application.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostCommand extends AuthOperation implements Command<Void> {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long postId;
}
