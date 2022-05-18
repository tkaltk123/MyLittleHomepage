package com.yunseojin.MyLittleHomepage.comment.dto;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest implements Serializable {
    @NotBlank(groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class
    }, message = "내용이 비어있습니다.")
    private String content;

    private Long parentId;
}
