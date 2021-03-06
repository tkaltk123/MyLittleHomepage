package com.yunseojin.MyLittleHomepage.post.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class ApiPostController {

    private final PostService postService;

    @GetMapping("/{post_id}")
    @ApiOperation(value = "게시글 조회", notes = "게시글 하나를 조회합니다.")
    public ResponseEntity<?> getPost(
            @PathVariable("post_id") Long postId) {

        return new ResponseEntity<>(postService.getPost(postId), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    public ResponseEntity<?> delete(
            @PathVariable("post_id") Long postId) {

        postService.deletePost(postId);

        return new ResponseEntity<>("게시물을 삭제했습니다.", HttpStatus.OK);
    }

    @PatchMapping("/{post_id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    public ResponseEntity<?> update(
            @PathVariable("post_id") Long postId,
            @RequestBody @Validated(ValidationGroups.Update.class) PostRequest postRequest) {

        return new ResponseEntity<>(postService.updatePost(postId, postRequest), HttpStatus.OK);
    }

    @GetMapping("/boards/{board_id}")
    @ApiOperation(value = "게시글 목록 조회", notes = "게시판의 게시글을 조회합니다.")
    public ResponseEntity<?> getPosts(
            @PathVariable("board_id") Long boardId,
            @RequestParam PostSearch postSearch) {

        return new ResponseEntity<>(postService.getPostList(boardId, 20, postSearch), HttpStatus.OK);
    }

    @PostMapping("/boards/{board_id}")
    @ApiOperation(value = "게시글 작성", notes = "게시판에 게시글을 작성합니다.")
    public ResponseEntity<?> create(
            @PathVariable(value = "board_id") Long boardId,
            @RequestBody @Validated(ValidationGroups.Create.class) PostRequest postRequest) {

        return new ResponseEntity<>(postService.createPost(boardId, postRequest), HttpStatus.OK);
    }


}
