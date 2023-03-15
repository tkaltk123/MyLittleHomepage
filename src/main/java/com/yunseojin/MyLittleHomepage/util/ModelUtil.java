package com.yunseojin.MyLittleHomepage.util;

import com.yunseojin.MyLittleHomepage.board.dto.BoardResponse;
import com.yunseojin.MyLittleHomepage.post.dto.FullPostSearch;
import com.yunseojin.MyLittleHomepage.v2.member.application.dto.MemberTokenDto;
import java.util.List;
import org.springframework.ui.Model;

public class ModelUtil {

    public static void setCommonAttr(Model model, MemberTokenDto memberInfo,
            List<BoardResponse> boardResponses) {

        setCommonAttr(model, memberInfo, boardResponses, new FullPostSearch());
    }

    public static void setCommonAttr(Model model, MemberTokenDto memberInfo,
            List<BoardResponse> boardResponses, FullPostSearch postSearch) {

        if (postSearch == null) {
            postSearch = new FullPostSearch();
        }

        if (memberInfo != null) {
            model.addAttribute("id", memberInfo.getId());
            model.addAttribute("loginId", memberInfo.getLoginId());
            model.addAttribute("nickname", memberInfo.getNickname());
        }
        model.addAttribute("boards", boardResponses);
        model.addAttribute("fullPostSearch", postSearch);
    }
}
