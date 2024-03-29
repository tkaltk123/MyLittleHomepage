package com.yunseojin.MyLittleHomepage.comment.controller;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.annotation.MemberId;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/comments")
@RestController
public class ApiCommentController {

    private final CommentService commentService;

    @GetMapping("/posts/{post_id}")
    @ApiOperation(value = "댓글 조회", notes = "게시글의 댓글을 조회합니다.")
    public ResponseEntity<?> getComments(
            @PathVariable("post_id") Long postId,
            @RequestParam(required = false, defaultValue = "0") Integer page) {

        return new ResponseEntity<>(commentService.getCommentList(postId, page), HttpStatus.OK);
    }

    @Login
    @PostMapping("/posts/{post_id}")
    @ApiOperation(value = "댓글 작성", notes = "게시글에 댓글을 작성합니다.")
    public ResponseEntity<?> create(
            @MemberId Long memberId,
            @PathVariable(value = "post_id") Long postId,
            @RequestBody CommentRequest commentRequest) {

        return new ResponseEntity<>(commentService.createComment(memberId, postId, commentRequest), HttpStatus.OK);
    }

    @Login
    @DeleteMapping("/{comment_id}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    public ResponseEntity<?> delete(
            @MemberId Long memberId,
            @PathVariable("comment_id") Long commentId) {

        commentService.deleteComment(memberId, commentId);
        return new ResponseEntity<>("댓글을 삭제했습니다.", HttpStatus.OK);
    }

    @Login
    @PatchMapping("")
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    public ResponseEntity<?> update(
            @MemberId Long memberId,
            @RequestBody @Validated(ValidationGroups.Update.class) CommentRequest commentRequest) {

        return new ResponseEntity<>(commentService.updateComment(memberId, commentRequest), HttpStatus.OK);
    }

}
