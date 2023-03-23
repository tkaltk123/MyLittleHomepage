package com.yunseojin.MyLittleHomepage.v2.application.post.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.domain.member.query.model.MemberContainer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostCommand extends MemberContainer implements Command<Void> {

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long postId;
}
