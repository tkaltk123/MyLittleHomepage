package com.yunseojin.MyLittleHomepage.v2.post.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Setter;

public class GetPostByIdQuery implements Query<PostResponse> {


    @JsonIgnore
    @Hidden
    @Setter
    private Long postId;
}
