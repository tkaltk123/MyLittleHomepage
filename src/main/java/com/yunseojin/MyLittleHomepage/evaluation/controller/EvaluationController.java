package com.yunseojin.MyLittleHomepage.evaluation.controller;

import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluationService;
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
    public ResponseEntity<?> likePost(
            @PathVariable(value = "post_id") Long postId) {
        return new ResponseEntity<>(evaluationService.likePost(postId), HttpStatus.OK);
    }
    @PostMapping("/like/comments/{comment_id}")
    public ResponseEntity<?> likeComment(
            @PathVariable(value = "comment_id") Long commentId) {
        return new ResponseEntity<>(evaluationService.likeComment(commentId), HttpStatus.OK);
    }
    @PostMapping("/dislike/posts/{post_id}")
    public ResponseEntity<?> dislikePost(
            @PathVariable(value = "post_id") Long postId) {
        return new ResponseEntity<>(evaluationService.dislikePost(postId), HttpStatus.OK);
    }
    @PostMapping("/dislike/comments/{comment_id}")
    public ResponseEntity<?> dislikeComment(
            @PathVariable(value = "comment_id") Long commentId) {
        return new ResponseEntity<>(evaluationService.dislikeComment(commentId), HttpStatus.OK);
    }
}
