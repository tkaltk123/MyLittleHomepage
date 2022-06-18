package com.yunseojin.MyLittleHomepage.etc.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.etc.enums.PostOrderType;
import com.yunseojin.MyLittleHomepage.etc.enums.PostSearchType;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import com.yunseojin.MyLittleHomepage.post.dto.PostSearch;
import com.yunseojin.MyLittleHomepage.post.service.PostService;
import com.yunseojin.MyLittleHomepage.util.ModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Resource
    private MemberInfo memberInfo;
    private final BoardService boardService;
    private final PostService postService;

    @GetMapping("")
    public String index(Model model) {

        model.addAttribute("orderTypes", PostOrderType.values());
        setOrderedFreePosts(model);
        setOrderedHumorPosts(model);
        ModelUtil.setCommonAttr(model, memberInfo, boardService.getBoardList());
        return "index";
    }

    private void setOrderedFreePosts(Model model) {

        var freeBoardId = boardService.getBoardByName("자유 게시판").getId();
        model.addAttribute("freePostsOrderedNew", postService.getPostList(freeBoardId, 10, new PostSearch()));
        model.addAttribute("freePostsOrderedLike", postService.getOrderedPostList(freeBoardId, 10, PostOrderType.LIKE));
        model.addAttribute("freePostsOrderedComment", postService.getOrderedPostList(freeBoardId, 10, PostOrderType.COMMENT));
        model.addAttribute("freePostsOrderedView", postService.getOrderedPostList(freeBoardId, 10, PostOrderType.VIEW));
    }

    private void setOrderedHumorPosts(Model model) {

        var humorBoardId = boardService.getBoardByName("유머 게시판").getId();
        model.addAttribute("humorPostsOrderedNew", postService.getPostList(humorBoardId, 10, new PostSearch()));
        model.addAttribute("humorPostsOrderedLike", postService.getOrderedPostList(humorBoardId, 10, PostOrderType.LIKE));
        model.addAttribute("humorPostsOrderedComment", postService.getOrderedPostList(humorBoardId, 10, PostOrderType.COMMENT));
        model.addAttribute("humorPostsOrderedView", postService.getOrderedPostList(humorBoardId, 10, PostOrderType.VIEW));
    }
}
