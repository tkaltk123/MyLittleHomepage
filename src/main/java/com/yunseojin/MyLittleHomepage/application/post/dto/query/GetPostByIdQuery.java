package com.yunseojin.MyLittleHomepage.application.post.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.application.post.dto.response.PostResponse;
import com.yunseojin.MyLittleHomepage.application.contract.dto.Query;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPostByIdQuery implements Query<PostResponse> {


    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Long postId;
}
