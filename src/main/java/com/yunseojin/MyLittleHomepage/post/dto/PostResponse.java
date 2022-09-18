package com.yunseojin.MyLittleHomepage.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long id;
    private Long boardId;
    private Long writerId;
    private String writerName;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private Date createdAt;
    private Date updatedAt;
    private String[] hashtags;
}
