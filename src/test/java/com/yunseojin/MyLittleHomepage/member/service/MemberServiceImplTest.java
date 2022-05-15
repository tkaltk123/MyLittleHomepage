package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.etc.enums.MemberType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceImpl memberService;

    @Test
    void resister() {
        //given
        var req = MemberRequest.builder()
                .loginId("login_id")
                .password("1234")
                .nickname("nickname")
                .build();
        //when
        memberService.resister(req);
        memberService.resister(req);
        var dbMember = memberRepository.findByLoginId(req.getLoginId());
        // then
        assertEquals(req.getNickname(), dbMember.getNickname());
        assertTrue(PasswordUtil.checkPassword(req.getPassword(), dbMember.getPassword()));
        assertEquals(MemberType.NORMAL, dbMember.getMemberType());
    }
}