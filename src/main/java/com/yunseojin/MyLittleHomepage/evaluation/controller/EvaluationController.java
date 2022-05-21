package com.yunseojin.MyLittleHomepage.evaluation.controller;

import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/evaluations")
@RestController
public class EvaluationController {
    private final EvaluationService evaluationService;

    @PostMapping("/like/posts/{post_id}")
    @ApiOperation(value = "게시글 좋아요", notes = "게시글에 좋아요를 추가합니다.")
    public ResponseEntity<?> likePost(
            @PathVariable(value = "post_id") Long postId) {
        return new ResponseEntity<>(evaluationService.likePost(postId), HttpStatus.OK);
    }

    @PostMapping("/like/comments/{comment_id}")
    @ApiOperation(value = "댓글 좋아요", notes = "댓글에 좋아요를 추가합니다.")
    public ResponseEntity<?> likeComment(
            @PathVariable(value = "comment_id") Long commentId) {
        return new ResponseEntity<>(evaluationService.likeComment(commentId), HttpStatus.OK);
    }

    @PostMapping("/dislike/posts/{post_id}")
    @ApiOperation(value = "게시글 싫어요", notes = "게시글에 싫어요를 추가합니다.")
    public ResponseEntity<?> dislikePost(
            @PathVariable(value = "post_id") Long postId) {
        return new ResponseEntity<>(evaluationService.dislikePost(postId), HttpStatus.OK);
    }

    @PostMapping("/dislike/comments/{comment_id}")
    @ApiOperation(value = "댓글 싫어요", notes = "게시글에 싫어요를 추가합니다.")
    public ResponseEntity<?> dislikeComment(
            @PathVariable(value = "comment_id") Long commentId) {
        return new ResponseEntity<>(evaluationService.dislikeComment(commentId), HttpStatus.OK);
    }
}
