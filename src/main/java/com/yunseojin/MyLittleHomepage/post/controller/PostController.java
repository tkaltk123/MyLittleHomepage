package com.yunseojin.MyLittleHomepage.post.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import com.yunseojin.MyLittleHomepage.util.ModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequiredArgsConstructor
@RequestMapping("/posts/{post_id}")
@Controller
public class PostController {

    @Resource
    private MemberInfo memberInfo;
    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("")
    public String getPost(
            Model model,
            @PathVariable(name = "post_id") Long postId,
            @RequestParam(required = false, name = "page", defaultValue = "0") Integer page) {

        setPostAttr(model, postId, page);
        ModelUtil.setCommonAttr(model, memberInfo, boardService.getBoardList());
        return "/layout/post";
    }

    @GetMapping("/modify")
    public String postUpdateForm(
            Model model,
            @PathVariable(name = "post_id") Long postId) {

        if (memberInfo.getId() == null)
            return "redirect:/login";

        var post = postService.getPost(postId);
        var postReq = PostRequest.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .hashTags(post.getHashtags())
                .build();
        model.addAttribute("postRequest", postReq);
        ModelUtil.setCommonAttr(model, memberInfo, boardService.getBoardList());
        return "layout/postForm";
    }

    @PostMapping("/modify")
    public String updatePost(
            @PathVariable(name = "post_id") Long postId,
            @Validated(ValidationGroups.Update.class) PostRequest postRequest) {

        postService.updatePost(postId, postRequest);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/delete")
    public String deletePost(@PathVariable(name = "post_id") Long postId) {

        var boardId = postService.getPost(postId).getBoardId();
        postService.deletePost(postId);
        return "redirect:/boards/" + boardId;
    }

    private void setPostAttr(Model model, Long postId, Integer page) {

        var commentPage = commentService.getCommentList(postId, page);
        var currentPage = commentPage.getNumber();
        var totalPage = commentPage.getTotalPages();
        var startPage = currentPage - currentPage % 5;
        var endPage = Math.max(0, Math.min(startPage + 4, totalPage - 1));

        model.addAttribute("post", postService.getPost(postId));
        model.addAttribute("commentRequest", new CommentRequest());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("comments", commentService.getCommentList(postId, page).getContent());
    }


}
