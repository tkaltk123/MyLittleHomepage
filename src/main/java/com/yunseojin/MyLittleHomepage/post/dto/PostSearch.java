package com.yunseojin.MyLittleHomepage.post.dto;

import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSearch {

    private Integer page = 0;
    private PostSearchType postSearchType = PostSearchType.TITLE;
    private String keyword = "";
}
