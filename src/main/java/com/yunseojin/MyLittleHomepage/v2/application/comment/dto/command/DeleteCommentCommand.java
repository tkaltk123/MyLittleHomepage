package com.yunseojin.MyLittleHomepage.v2.application.comment.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.MemberContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCommentCommand extends MemberContainer implements Command<Void> {

    @JsonIgnore
    private Long commentId;
}
