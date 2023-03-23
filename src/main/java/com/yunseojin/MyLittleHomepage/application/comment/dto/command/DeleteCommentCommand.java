package com.yunseojin.MyLittleHomepage.application.comment.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Command;
import com.yunseojin.MyLittleHomepage.domain.member.query.model.MemberContainer;
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
