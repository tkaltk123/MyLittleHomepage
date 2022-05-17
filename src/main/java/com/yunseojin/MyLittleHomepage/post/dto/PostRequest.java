package com.yunseojin.MyLittleHomepage.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest implements Serializable {
    @Max(value = 255, groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class,
    }, message = "제목은 최대 255글자 입니다")
    @NotBlank(groups = {
            ValidationGroups.Create.class,
            ValidationGroups.Update.class
    }, message = "제목이 비어있습니다.")
    private String title;

    private String content;

    @JsonProperty
    private String[] hashTags;
}
