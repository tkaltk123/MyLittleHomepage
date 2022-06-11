package com.yunseojin.MyLittleHomepage.comment.dto;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest implements Serializable {
    @NotBlank(groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class
    }, message = "내용이 비어있습니다.")
    private String content = "";

    private Long parentId;
}
