package com.yunseojin.MyLittleHomepage.v2.comment.application.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.CommentCommand;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentCommand extends CommentCommand<CommentResponse> {

    @JsonIgnore
    @Hidden
    @Setter
    private Long commentId;
}
