package com.yunseojin.MyLittleHomepage.util;

import com.yunseojin.MyLittleHomepage.board.dto.BoardResponse;
import com.yunseojin.MyLittleHomepage.member.dto.MemberInfo;
import org.springframework.ui.Model;

import java.util.List;

public class ModelUtil {

    public static void setCommonAttr(Model model, MemberInfo memberInfo, List<BoardResponse> boardResponses){

        model.addAttribute("id", memberInfo.getId());
        model.addAttribute("loginId", memberInfo.getLoginId());
        model.addAttribute("nickname", memberInfo.getNickname());
        model.addAttribute("boards", boardResponses);
    }
}
