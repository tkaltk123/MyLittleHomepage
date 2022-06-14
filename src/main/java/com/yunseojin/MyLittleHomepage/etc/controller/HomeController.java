package com.yunseojin.MyLittleHomepage.etc.controller;

import com.yunseojin.MyLittleHomepage.board.service.BoardService;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
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

    @GetMapping("")
    public String index(Model model) {

        ModelUtil.setCommonAttr(model, memberInfo, boardService.getBoardList());
        return "index";
    }
}
