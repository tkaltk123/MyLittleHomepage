package com.yunseojin.MyLittleHomepage.controller.feedback;

import com.yunseojin.MyLittleHomepage.application.contract.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.comment.CommentDislikeCommand;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.comment.CommentLikeCommand;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.post.PostDislikeCommand;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.command.post.PostLikeCommand;
import com.yunseojin.MyLittleHomepage.application.feedback.dto.response.FeedbackResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/feedbacks")
@RestController
public class ApiFeedbackController {

    private final ApplicationService applicationService;

    @PostMapping("/like/posts/{post_id}")
    @ApiOperation(value = "게시글 좋아요", notes = "게시글에 좋아요를 추가합니다.")
    public ResponseEntity<FeedbackResponse> likePost(@PathVariable(value = "post_id") Long postId) {
        return ResponseEntity.ok(applicationService.executeCommand(new PostLikeCommand(postId)));
    }

    @PostMapping("/like/comments/{comment_id}")
    @ApiOperation(value = "댓글 좋아요", notes = "댓글에 좋아요를 추가합니다.")
    public ResponseEntity<FeedbackResponse> likeComment(
            @PathVariable(value = "comment_id") Long commentId) {
        return ResponseEntity.ok(applicationService.executeCommand(
                new CommentLikeCommand(commentId)));
    }

    @PostMapping("/dislike/posts/{post_id}")
    @ApiOperation(value = "게시글 싫어요", notes = "게시글에 싫어요를 추가합니다.")
    public ResponseEntity<FeedbackResponse> dislikePost(
            @PathVariable(value = "post_id") Long postId) {
        return ResponseEntity.ok(applicationService.executeCommand(new PostDislikeCommand(postId)));
    }

    @PostMapping("/dislike/comments/{comment_id}")
    @ApiOperation(value = "댓글 싫어요", notes = "댓글에 싫어요를 추가합니다.")
    public ResponseEntity<FeedbackResponse> dislikeComment(
            @PathVariable(value = "comment_id") Long commentId) {
        return ResponseEntity.ok(applicationService.executeCommand(
                new CommentDislikeCommand(commentId)));
    }
}
