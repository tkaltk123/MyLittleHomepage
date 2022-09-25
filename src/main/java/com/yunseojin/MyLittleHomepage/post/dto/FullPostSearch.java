package com.yunseojin.MyLittleHomepage.post.dto;

import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FullPostSearch extends PostSearch {

    private Long boardId = null;
    private Boolean isAsc = false;

    public FullPostSearch(PostSearchType postSearchType, String keyword, Long boardId, Boolean isAsc) {

        super(0, postSearchType, keyword);
        this.boardId = boardId;
        this.isAsc = isAsc;
    }

}
