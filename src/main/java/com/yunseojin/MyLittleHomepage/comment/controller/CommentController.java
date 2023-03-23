package com.yunseojin.MyLittleHomepage.comment.controller;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.v2.application.member.dto.MemberTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
@ApiIgnore
public class CommentController {

    private final CommentService commentService;

    @Login
    @PostMapping("/posts/{post_id}")
    public String createComment(
            MemberTokenDto memberTokenDto,
            @PathVariable(name = "post_id") Long postId,
            @Validated(ValidationGroups.Create.class) CommentRequest commentRequest) {

        var comment = commentService.createComment(memberTokenDto.getId(), postId, commentRequest);
        return "redirect:/posts/" + comment.getPostId();
    }

    @Login
    @PostMapping("/modify")
    public String updateComment(
            MemberTokenDto memberTokenDto,
            @Validated(ValidationGroups.Update.class) CommentRequest commentRequest) {

        var comment = commentService.updateComment(memberTokenDto.getId(), commentRequest);
        return "redirect:/posts/" + comment.getPostId();
    }

    @Login
    @PostMapping("/{comment_id}/delete")
    public String deleteComment(
            MemberTokenDto memberTokenDto,
            @PathVariable(name = "comment_id") Long commentId) {

        var comment = commentService.getComment(commentId);
        commentService.deleteComment(memberTokenDto.getId(), commentId);
        return "redirect:/posts/" + comment.getPostId();
    }
}
