package com.yunseojin.MyLittleHomepage.evaluation.controller;

import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class EvaluationController {

    private final CommentService commentService;
    private final EvaluationService evaluationService;

    @PostMapping("/like/posts/{post_id}")
    public String likePost(@PathVariable(name = "post_id") Long postId) {

        evaluationService.likePost(postId);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/like/comments/{comment_id}")
    public String likeComment(@PathVariable(name = "comment_id") Long commentId) {

        var comment = commentService.getComment(commentId);

        evaluationService.likeComment(commentId);

        return "redirect:/posts/" + comment.getPostId();
    }

    @PostMapping("/dislike/posts/{post_id}")
    public String dislikePost(@PathVariable(name = "post_id") Long postId) {

        evaluationService.dislikePost(postId);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/dislike/comments/{comment_id}")
    public String dislikeComment(@PathVariable(name = "comment_id") Long commentId) {

        var comment = commentService.getComment(commentId);

        evaluationService.dislikeComment(commentId);

        return "redirect:/posts/" + comment.getPostId();
    }

}
