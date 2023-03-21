package com.yunseojin.MyLittleHomepage.v2.controller.comment;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.command.CreateCommentCommand;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.command.DeleteCommentCommand;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.command.UpdateCommentCommand;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.query.GetCommentsQuery;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponse;
import com.yunseojin.MyLittleHomepage.v2.comment.application.dto.response.CommentResponseWithChildren;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.ApplicationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v2/comments")
@RestController
public class ApiCommentControllerV2 {

    private final ApplicationService applicationService;

    @GetMapping("/posts/{post_id}")
    @ApiOperation(value = "댓글 조회", notes = "게시글의 댓글을 조회합니다.")
    public ResponseEntity<Page<CommentResponseWithChildren>> getComments(
            @PathVariable("post_id") Long postId,
            @ModelAttribute GetCommentsQuery query) {

        query.setPostId(postId);

        return ResponseEntity.ok(applicationService.executeQuery(query));
    }

    @Login
    @PostMapping("/posts/{post_id}")
    @ApiOperation(value = "댓글 작성", notes = "게시글에 댓글을 작성합니다.")
    public ResponseEntity<CommentResponse> create(
            @PathVariable(value = "post_id") Long postId,
            @RequestBody CreateCommentCommand command) {

        command.setPostId(postId);

        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @Login
    @DeleteMapping("/{comment_id}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
    public ResponseEntity<Void> delete(
            @PathVariable("comment_id") Long commentId) {

        var command = new DeleteCommentCommand(commentId);

        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @Login
    @PatchMapping("/{comment_id}")
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다.")
    public ResponseEntity<CommentResponse> update(
            @PathVariable("comment_id") Long commentId,
            @RequestBody UpdateCommentCommand command) {

        command.setCommentId(commentId);

        return ResponseEntity.ok(applicationService.executeCommand(command));
    }
}
