package com.yunseojin.MyLittleHomepage.board.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.etc.annotation.ValidationGroups;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.post.dto.PostRequest;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import com.yunseojin.MyLittleHomepage.util.ModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequiredArgsConstructor
@RequestMapping("/boards/{board_id}")
@Controller
public class BoardController {

    @Resource
    private MemberInfo memberInfo;
    private final BoardService boardService;
    private final PostService postService;

    @GetMapping("")
    public String getBoard(
            Model model,
            @PathVariable(name = "board_id") Long boardId,
            @ModelAttribute(name = "postSearch") PostSearch postSearch) {

        setPostSearchAttribute(model, boardId, postSearch);
        ModelUtil.setCommonAttr(model, memberInfo, boardService.getBoardList());
        return "layout/board";
    }

    @GetMapping("/write_post")
    public String postWriteForm(
            Model model,
            @PathVariable(name = "board_id") Long boardId) {

        if (memberInfo.getId() == null)
            return "redirect:/login";

        model.addAttribute("postRequest", new PostRequest());
        ModelUtil.setCommonAttr(model, memberInfo, boardService.getBoardList());
        return "layout/postForm";
    }

    @PostMapping("/write_post")
    public String createPost(
            @PathVariable(name = "board_id") Long boardId,
            @Validated(ValidationGroups.Create.class) PostRequest postRequest) {

        var postRes = postService.createPost(boardId, postRequest);
        return "redirect:/posts/" + postRes.getId();
    }

    private void setPostSearchAttribute(Model model, Long boardId, PostSearch postSearch) {

        var postPage = postService.getPostList(boardId, postSearch);
        var currentPage = postPage.getNumber();
        var startPage = currentPage - currentPage % 5;
        var endPage = Math.max(0, Math.min(startPage + 4, postPage.getTotalPages() - 1));

        model.addAttribute("searchTypes", PostSearchType.values());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPage", postPage.getTotalPages());
        model.addAttribute("posts", postPage.getContent());
    }
}
