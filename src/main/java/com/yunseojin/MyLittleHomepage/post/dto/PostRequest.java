package com.yunseojin.MyLittleHomepage.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest implements Serializable {

    @Size(min = 2, max = 255, groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class,
    }, message = "제목은 2~255글자 입니다")
    @NotBlank(groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class
    }, message = "제목이 비어있습니다.")
    private String title;

    private String content;

    @JsonProperty
    private String[] hashTags;
}
