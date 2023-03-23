package com.yunseojin.MyLittleHomepage.application.comment.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.application.comment.dto.CommentCommand;
import com.yunseojin.MyLittleHomepage.application.comment.dto.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentCommand extends CommentCommand<CommentResponse> {

    @JsonIgnore
    private Long commentId;
}
