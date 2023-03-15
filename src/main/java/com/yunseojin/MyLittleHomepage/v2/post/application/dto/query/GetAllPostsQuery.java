package com.yunseojin.MyLittleHomepage.v2.post.application.dto.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.v2.contract.application.dto.Query;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostsQuery implements Query<Page<PostResponse>> {

    @JsonIgnore
    @Hidden
    @Setter
    private Long boardId;

    private PostSearchType postSearchType;

    private String keyword;

}
