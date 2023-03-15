package com.yunseojin.MyLittleHomepage.v2.post.application.dto.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.PostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostCommand extends PostCommand<PostResponse> {

    @JsonIgnore
    @Hidden
    @Setter
    private Long postId;
}
