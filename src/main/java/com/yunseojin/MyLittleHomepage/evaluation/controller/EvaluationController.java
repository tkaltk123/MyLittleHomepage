package com.yunseojin.MyLittleHomepage.evaluation.controller;

import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.MemberToken;
import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluationService;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@Controller
@ApiIgnore
public class EvaluationController {

    private final CommentService commentService;
    private final EvaluationService evaluationService;

    @PostMapping("/like/posts/{post_id}")
    public String likePost(
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "post_id") Long postId) {

        evaluationService.likePost(memberTokenDto.getId(), postId);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/like/comments/{comment_id}")
    public String likeComment(
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "comment_id") Long commentId) {

        var comment = commentService.getComment(commentId);

        evaluationService.likeComment(memberTokenDto.getId(), commentId);

        return "redirect:/posts/" + comment.getPostId();
    }

    @PostMapping("/dislike/posts/{post_id}")
    public String dislikePost(
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "post_id") Long postId) {

        evaluationService.dislikePost(memberTokenDto.getId(), postId);

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/dislike/comments/{comment_id}")
    public String dislikeComment(
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "comment_id") Long commentId) {

        var comment = commentService.getComment(commentId);

        evaluationService.dislikeComment(memberTokenDto.getId(), commentId);

        return "redirect:/posts/" + comment.getPostId();
    }

}
