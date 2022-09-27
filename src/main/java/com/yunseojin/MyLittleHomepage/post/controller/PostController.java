package com.yunseojin.MyLittleHomepage.post.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentResponse;
import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.annotation.MemberToken;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
import com.yunseojin.MyLittleHomepage.post.dto.FullPostSearch;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.mapper.PostMapper;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import com.yunseojin.MyLittleHomepage.util.ModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
@ApiIgnore
public class PostController {

    private final BoardService boardService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/{post_id}")
    public String getPost(
            Model model,
            HttpServletRequest request,
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "post_id") Long postId,
            @RequestParam(required = false, name = "page", defaultValue = "0") Integer page) {

        var commentPage = commentService.getCommentList(postId, page);

        postService.viewPost(request.getRemoteAddr(), postId);
        model.addAttribute("post", postService.getPost(postId));
        setCommentsAttr(model, commentPage);
        ModelUtil.setCommonAttr(model, memberTokenDto, boardService.getBoardList());

        return "/layout/post";
    }

    @GetMapping("/search")
    public String getPostInSearch(
            Model model,
            @MemberToken MemberTokenDto memberTokenDto,
            @ModelAttribute(name = "fullPostSearch") FullPostSearch postSearch) {


        var postPage = postService.getPostListWithCursor(null, 20, postSearch);

        model.addAttribute("posts", postPage);
        ModelUtil.setCommonAttr(model, memberTokenDto, boardService.getBoardList(), postSearch);

        return "layout/search";
    }

    @GetMapping("/{post_id}/modify")
    public String postUpdateForm(
            Model model,
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "post_id") Long postId) {

        if (!MemberTokenDto.isLoggedIn(memberTokenDto))
            return "redirect:/login";

        var post = postService.getPost(postId);
        var postReq = PostMapper.INSTANCE.toPostRequest(post);

        model.addAttribute("postRequest", postReq);
        ModelUtil.setCommonAttr(model, memberTokenDto, boardService.getBoardList());

        return "layout/postForm";
    }

    @Login
    @PostMapping("/{post_id}/modify")
    public String updatePost(
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "post_id") Long postId,
            @Validated(ValidationGroups.Update.class) PostRequest postRequest) {

        postService.updatePost(memberTokenDto.getId(), postId, postRequest);

        return "redirect:/posts/" + postId;
    }

    @Login
    @PostMapping("/{post_id}/delete")
    public String deletePost(
            @MemberToken MemberTokenDto memberTokenDto,
            @PathVariable(name = "post_id") Long postId) {

        var boardId = postService.getPost(postId).getBoardId();

        postService.deletePost(memberTokenDto.getId(), postId);

        return "redirect:/boards/" + boardId;
    }

    private void setCommentsAttr(Model model, Page<CommentResponse> commentPage) {

        var currentPage = commentPage.getNumber();
        var totalPage = commentPage.getTotalPages();
        var startPage = currentPage - currentPage % 5;
        var endPage = Math.max(0, Math.min(startPage + 4, totalPage - 1));

        model.addAttribute("commentRequest", new CommentRequest());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("comments", commentPage.getContent());
    }
}
