package com.yunseojin.MyLittleHomepage.v2.controller.post;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.v2.config.web.resolver.LoginUser;
import com.yunseojin.MyLittleHomepage.v2.contract.application.service.ApplicationService;
import com.yunseojin.MyLittleHomepage.v2.member.domain.model.Member;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.command.CreatePostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.command.DeletePostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.command.UpdatePostCommand;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.query.GetAllPostsQuery;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.query.GetPostByIdQuery;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.query.GetPostSearchQuery;
import com.yunseojin.MyLittleHomepage.v2.post.application.dto.response.PostResponse;
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
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/api/v2/posts")
@RestController
public class ApiPostControllerV2 {

    private final ApplicationService applicationService;

    @GetMapping("/{post_id}")
    @ApiOperation(value = "게시글 조회", notes = "게시글 하나를 조회합니다.")
    public ResponseEntity<PostResponse> getPost(@PathVariable("post_id") Long postId) {

        var query = new GetPostByIdQuery();
        query.setPostId(postId);

        return ResponseEntity.ok(applicationService.executeQuery(query));
    }

    @Login
    @DeleteMapping("/{post_id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    public ResponseEntity<Void> delete(
            @ApiIgnore @LoginUser Member member,
            @PathVariable("post_id") Long postId) {

        var command = new DeletePostCommand();
        command.setMember(member);
        command.setPostId(postId);

        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @Login
    @PatchMapping("/{post_id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    public ResponseEntity<PostResponse> update(
            @ApiIgnore @LoginUser Member member,
            @PathVariable("post_id") Long postId,
            @RequestBody UpdatePostCommand command) {

        command.setMember(member);
        command.setPostId(postId);

        return ResponseEntity.ok(applicationService.executeCommand(command));
    }

    @GetMapping("/boards/{board_id}")
    @ApiOperation(value = "게시글 목록 조회", notes = "게시판의 게시글을 조회합니다.")
    public ResponseEntity<Page<PostResponse>> getPosts(
            @PathVariable("board_id") Long boardId,
            @ModelAttribute GetAllPostsQuery query) {

        query.setBoardId(boardId);

        return ResponseEntity.ok(applicationService.executeQuery(query));
    }

    @GetMapping("/search")
    @ApiOperation(value = "게시글 목록 조회", notes = "전체 게시판의 게시글을 조회합니다.")
    public ResponseEntity<Page<PostResponse>> postFullSearch(
            @ModelAttribute GetPostSearchQuery query) {

        return ResponseEntity.ok(applicationService.executeQuery(query));
    }

    @Login
    @PostMapping("/boards/{board_id}")
    @ApiOperation(value = "게시글 작성", notes = "게시판에 게시글을 작성합니다.")
    public ResponseEntity<PostResponse> create(
            @ApiIgnore @LoginUser Member member,
            @PathVariable(value = "board_id") Long boardId,
            @RequestBody CreatePostCommand command) {

        command.setMember(member);
        command.setBoardId(boardId);

        return ResponseEntity.ok(applicationService.executeCommand(command));
    }


}
