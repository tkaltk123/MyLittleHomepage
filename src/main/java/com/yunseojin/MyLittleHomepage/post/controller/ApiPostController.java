package com.yunseojin.MyLittleHomepage.post.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.annotation.MemberId;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class ApiPostController {

    private final PostService postService;

    @GetMapping("/{post_id}")
    @ApiOperation(value = "게시글 조회", notes = "게시글 하나를 조회합니다.")
    public ResponseEntity<?> getPost(
            HttpServletRequest request,
            @PathVariable("post_id") Long postId) {

        postService.viewPost(request.getRemoteAddr(), postId);

        return new ResponseEntity<>(postService.getPost(postId), HttpStatus.OK);
    }

    @Login
    @DeleteMapping("/{post_id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    public ResponseEntity<?> delete(
            @MemberId Long memberId,
            @PathVariable("post_id") Long postId) {

        postService.deletePost(memberId, postId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Login
    @PatchMapping("/{post_id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    public ResponseEntity<?> update(
            @MemberId Long memberId,
            @PathVariable("post_id") Long postId,
            @RequestBody @Validated(ValidationGroups.Update.class) PostRequest postRequest) {

        return new ResponseEntity<>(postService.updatePost(memberId, postId, postRequest), HttpStatus.OK);
    }

    @GetMapping("/boards/{board_id}")
    @ApiOperation(value = "게시글 목록 조회", notes = "게시판의 게시글을 조회합니다.")
    public ResponseEntity<?> getPosts(
            @PathVariable("board_id") Long boardId,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "TITLE", required = false) PostSearchType postSearchType,
            @RequestParam(required = false) String keyword) {

        return new ResponseEntity<>(postService.getPostList(boardId, 20, new PostSearch(page, postSearchType, keyword)), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation(value = "게시글 목록 조회", notes = "전체 게시판의 게시글을 조회합니다.")
    public ResponseEntity<?> postFullSearch(
            @RequestParam(required = false) Long postId,
            @RequestParam(required = false) Long boardId,
            @RequestParam(defaultValue = "TITLE", required = false) PostSearchType postSearchType,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "false", required = false) boolean isAsc) {

        return new ResponseEntity<>(postService.getPostListWithCursor(postId, boardId, 20, new PostSearch(0, postSearchType, keyword), isAsc), HttpStatus.OK);
    }

    @Login
    @PostMapping("/boards/{board_id}")
    @ApiOperation(value = "게시글 작성", notes = "게시판에 게시글을 작성합니다.")
    public ResponseEntity<?> create(
            @MemberId Long memberId,
            @PathVariable(value = "board_id") Long boardId,
            @RequestBody @Validated(ValidationGroups.Create.class) PostRequest postRequest) {

        return new ResponseEntity<>(postService.createPost(memberId, boardId, postRequest), HttpStatus.OK);
    }


}
