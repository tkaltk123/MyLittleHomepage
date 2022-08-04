package com.yunseojin.MyLittleHomepage.comment.dto;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest implements Serializable {

    @NotBlank(groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class
    }, message = "내용이 비어있습니다.")
    @Builder.Default
    private String content = "";

    private Long parentId;

    @NotNull(groups = {
            ValidationGroups.Update.class
    }, message = "수정할 댓글이 비어있습니다.")
    private Long commentId;
}
