package com.yunseojin.MyLittleHomepage.comment.controller;

import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{post_id}")
    public String createComment(
            @PathVariable(name = "post_id") Long postId,
            @Validated(ValidationGroups.Create.class) CommentRequest commentRequest) {

        var comment = commentService.createComment(postId, commentRequest);
        return "redirect:/posts/" + comment.getPostId();
    }

    @PostMapping("/modify")
    public String updateComment(
            @Validated(ValidationGroups.Update.class) CommentRequest commentRequest) {

        var comment = commentService.updateComment(commentRequest);
        return "redirect:/posts/" + comment.getPostId();
    }

    @PostMapping("/{comment_id}/delete")
    public String deleteComment(
            @PathVariable(name = "comment_id") Long commentId) {

        var comment = commentService.getComment(commentId);
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + comment.getPostId();
    }
}
