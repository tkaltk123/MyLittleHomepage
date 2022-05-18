package com.yunseojin.MyLittleHomepage.comment.controller;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{post_id}")
    public ResponseEntity<?> create(
            @PathVariable(value = "board_id") Long boardId,
            @RequestBody CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.createComment(boardId, commentRequest), HttpStatus.OK);
    }

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<?> getComments(
            @PathVariable("board_id") Long boardId,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(commentService.getCommentList(boardId, page), HttpStatus.OK);
    }

    @PatchMapping("/{comment_id}")
    public ResponseEntity<?> update(
            @PathVariable("post_id") Long postId,
            @RequestBody @Validated(ValidationGroups.Update.class) CommentRequest commentRequest) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<?> delete(
            @PathVariable("post_id") Long postId) {
        commentService.deleteComment(postId);
        return new ResponseEntity<>("댓글을 삭제했습니다.", HttpStatus.OK);
    }
}
