package com.yunseojin.MyLittleHomepage.post.dto;

import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSearch {

    private Integer page = 0;
    private PostSearchType postSearchType = PostSearchType.TITLE;
    private String keyword = "";
}
