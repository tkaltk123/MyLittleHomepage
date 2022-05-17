package com.yunseojin.MyLittleHomepage.post.controller;

import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/boards/{board_id}/posts")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> create(
            @PathVariable(value = "board_id") Long boardId,
            @RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(postService.createPost(boardId, postRequest), HttpStatus.OK);
    }

    @PatchMapping("/{post_id}")
    public ResponseEntity<?> update(
            @PathVariable("post_id") Long postId,
            @RequestBody @Validated(ValidationGroups.Update.class) PostRequest postRequest) {
        return new ResponseEntity<>(postService.updatePost(postId, postRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> delete(
            @PathVariable("post_id") Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>("게시물을 삭제했습니다.", HttpStatus.OK);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<?> getPost(
            @PathVariable("post_id") Long postId) {
        return new ResponseEntity<>(postService.getPost(postId), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getPosts(
            @PathVariable("board_id") Long boardId,
            @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(postService.getPostList(boardId, page), HttpStatus.OK);
    }
}
