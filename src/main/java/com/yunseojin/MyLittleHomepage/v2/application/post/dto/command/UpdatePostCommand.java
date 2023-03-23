package com.yunseojin.MyLittleHomepage.v2.application.post.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.v2.application.post.dto.PostCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostCommand extends PostCommand<PostResponse> {

    @JsonIgnore
    private Long postId;
}
