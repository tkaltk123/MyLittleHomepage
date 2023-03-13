package com.yunseojin.MyLittleHomepage.board.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.etc.annotation.Login;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.member.dto.MemberTokenDto;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import com.yunseojin.MyLittleHomepage.util.ModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping("/boards/{board_id}")
@Controller
@ApiIgnore
public class BoardController {

    private final BoardService boardService;
    private final PostService postService;

    @GetMapping("")
    public String getBoard(
            Model model,
            MemberTokenDto memberTokenDto,
            @PathVariable(name = "board_id") Long boardId,
            @ModelAttribute(name = "postSearch") PostSearch postSearch) {

        setPostSearchAttribute(model, boardId, postSearch);
        ModelUtil.setCommonAttr(model, memberTokenDto, boardService.getBoardList());
        return "layout/board";
    }

    @GetMapping("/write_post")
    public String postWriteForm(
            Model model,
            MemberTokenDto memberTokenDto,
            @PathVariable(name = "board_id") Long boardId) {

        if (!MemberTokenDto.isLoggedIn(memberTokenDto))
            return "redirect:/login";

        model.addAttribute("postRequest", new PostRequest());
        ModelUtil.setCommonAttr(model, memberTokenDto, boardService.getBoardList());
        return "layout/postForm";
    }

    @Login
    @PostMapping("/write_post")
    public String createPost(
            MemberTokenDto memberTokenDto,
            @PathVariable(name = "board_id") Long boardId,
            @Validated(ValidationGroups.Create.class) PostRequest postRequest) {

        var postRes = postService.createPost(memberTokenDto.getId(), boardId, postRequest);
        return "redirect:/posts/" + postRes.getId();
    }

    private void setPostSearchAttribute(Model model, Long boardId, PostSearch postSearch) {

        var postPage = postService.getPostList(boardId, 20, postSearch);
        var currentPage = postPage.getNumber();
        var startPage = currentPage - currentPage % 5;
        var endPage = Math.max(0, Math.min(startPage + 4, postPage.getTotalPages() - 1));
        ;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", postPage.getTotalPages());
        model.addAttribute("posts", postPage.getContent());
    }
}
