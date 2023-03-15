package com.yunseojin.MyLittleHomepage.v2.post.application.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.AuthOperation;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostCommand extends AuthOperation implements Command<Void> {

    @JsonIgnore
    @Hidden
    @Setter
    private Long postId;
}
