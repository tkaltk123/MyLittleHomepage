package com.yunseojin.MyLittleHomepage.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private Long postId;
    private Long writerId;
    private String writerName;
    private String content;
    private Integer likeCount;
    private Integer dislikeCount;
    private Date createdAt;
    private Date updatedAt;
    private CommentResponse[] children;
}
