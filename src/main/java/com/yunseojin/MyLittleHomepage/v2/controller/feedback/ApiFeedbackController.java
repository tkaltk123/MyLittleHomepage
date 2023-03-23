package com.yunseojin.MyLittleHomepage.v2.controller.feedback;

import com.yunseojin.MyLittleHomepage.v2.application.contract.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command.CommentFeedbackCommand;
import com.yunseojin.MyLittleHomepage.v2.application.feedback.dto.command.PostFeedbackCommand;
import com.yunseojin.MyLittleHomepage.v2.domain.feedback.vo.FeedbackType;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v2/feedbacks")
@RestController
public class ApiFeedbackController {

    private final ApplicationService applicationService;

    @PostMapping("/like/posts/{post_id}")
    @ApiOperation(value = "게시글 좋아요", notes = "게시글에 좋아요를 추가합니다.")
    public ResponseEntity<FeedbackType> likePost(@PathVariable(value = "post_id") Long postId) {

        var command = new PostFeedbackCommand(postId);
        command.setFeedbackType(FeedbackType.LIKE);
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @PostMapping("/like/comments/{comment_id}")
    @ApiOperation(value = "댓글 좋아요", notes = "댓글에 좋아요를 추가합니다.")
    public ResponseEntity<?> likeComment(@PathVariable(value = "comment_id") Long commentId) {

        var command = new CommentFeedbackCommand(commentId);
        command.setFeedbackType(FeedbackType.LIKE);
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @PostMapping("/dislike/posts/{post_id}")
    @ApiOperation(value = "게시글 싫어요", notes = "게시글에 싫어요를 추가합니다.")
    public ResponseEntity<?> dislikePost(@PathVariable(value = "post_id") Long postId) {

        var command = new PostFeedbackCommand(postId);
        command.setFeedbackType(FeedbackType.DISLIKE);
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @PostMapping("/dislike/comments/{comment_id}")
    @ApiOperation(value = "댓글 싫어요", notes = "댓글에 싫어요를 추가합니다.")
    public ResponseEntity<?> dislikeComment(@PathVariable(value = "comment_id") Long commentId) {

        var command = new CommentFeedbackCommand(commentId);
        command.setFeedbackType(FeedbackType.DISLIKE);
        return ResponseEntity.ok(applicationService.executeCommand(command));
    }
}
