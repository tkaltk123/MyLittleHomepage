package com.yunseojin.MyLittleHomepage.view.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.comment.dto.CommentRequest;
import com.yunseojin.MyLittleHomepage.comment.service.CommentService;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.evaluation.service.EvaluationService;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.service.MemberService;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class ViewController {
    @Resource
    private MemberInfo memberInfo;
    private final BoardService boardService;
    private final MemberService memberService;
    private final PostService postService;
    private final EvaluationService evaluationService;
    private final CommentService commentService;

    @GetMapping("")
    public String index(Model model) {

        setCommonAttribute(model);
        return "index";
    }

    @GetMapping("/login")
    public String loginFrom(Model model, HttpServletRequest request) {

        if (memberInfo.getId() != null)
            return "redirect:/";

        model.addAttribute("memberRequest", new MemberRequest());
        model.addAttribute("referer", request.getHeader("Referer"));

        return "layout/login";
    }

    @PostMapping(value = "/login")
    public String login(
            MemberRequest memberRequest,
            @RequestParam(required = false, name = "referer", defaultValue = "/") String referer) {

        memberService.login(memberRequest);
        return "redirect:" + referer;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        memberService.logout();
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        if (memberInfo.getId() != null)
            return "redirect:/";

        model.addAttribute("memberRequest", new MemberRequest());
        return "layout/register";
    }

    @PostMapping(value = "/register")
    public String register(
            @Validated(ValidationGroups.Register.class) MemberRequest memberRequest) {

        memberService.register(memberRequest);
        return "redirect:/";
    }

    @GetMapping("/boards/{board_id}")
    public String board(
            Model model,
            @PathVariable(name = "board_id") Long boardId,
            @ModelAttribute(name = "postSearch") PostSearch postSearch) {

        setPostSearchAttribute(model, boardId, postSearch);
        setCommonAttribute(model);
        return "layout/board";
    }

    @GetMapping("/posts/write/{board_id}")
    public String postWriteForm(
            Model model,
            @PathVariable(name = "board_id") Long boardId) {

        if (memberInfo.getId() == null)
            return "redirect:/login";
        model.addAttribute("postRequest", new PostRequest());
        setCommonAttribute(model);
        return "layout/postForm";
    }

    @PostMapping("/posts/write/{board_id}")
    public String createPost(
            @PathVariable(name = "board_id") Long boardId,
            @Validated(ValidationGroups.Create.class) PostRequest postRequest) {

        var postRes = postService.createPost(boardId, postRequest);
        return "redirect:/posts/" + postRes.getId();
    }

    @GetMapping("/posts/{post_id}")
    public String getPost(
            Model model,
            @PathVariable(name = "post_id") Long postId,
            @RequestParam(required = false, defaultValue = "0") Integer page) {

        model.addAttribute("post", postService.getPost(postId));
        model.addAttribute("commentRequest", new CommentRequest());
        model.addAttribute("comments", commentService.getCommentList(postId, page));
        setCommonAttribute(model);
        return "/layout/post";
    }

    @PostMapping("/comments/posts/{post_id}")
    public String createComment(
            @PathVariable(name = "post_id") Long postId,
            @Validated(ValidationGroups.Create.class) CommentRequest commentRequest
            , HttpServletRequest request) {

        commentService.createComment(postId, commentRequest);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/like/posts/{post_id}")
    public String likePost(@PathVariable(name = "post_id") Long postId, HttpServletRequest request) {

        evaluationService.likePost(postId);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/dislike/posts/{post_id}")
    public String dislikePost(@PathVariable(name = "post_id") Long postId, HttpServletRequest request) {

        evaluationService.dislikePost(postId);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/like/comments/{comment_id}")
    public String likeComment(@PathVariable(name = "comment_id") Long commentId, HttpServletRequest request) {

        evaluationService.likeComment(commentId);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/dislike/comments/{comment_id}")
    public String dislikeComment(@PathVariable(name = "comment_id") Long commentId, HttpServletRequest request) {

        evaluationService.dislikeComment(commentId);
        return "redirect:" + request.getHeader("Referer");
    }


    private void setCommonAttribute(Model model) {

        model.addAttribute("login_id", memberInfo.getLoginId());
        model.addAttribute("nickname", memberInfo.getNickname());
        model.addAttribute("boards", boardService.getBoardList());
    }

    private void setPostSearchAttribute(Model model, Long boardId, PostSearch postSearch) {

        var postPage = postService.getPostList(boardId, postSearch);
        var currenPage = postPage.getNumber();
        var startPage = currenPage - currenPage % 5;
        var endPage = Math.max(0, Math.min(startPage + 4, postPage.getTotalPages() - 1));

        model.addAttribute("searchTypes", PostSearchType.values());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", postPage.getTotalPages());
        model.addAttribute("posts", postPage.getContent());
    }

}
