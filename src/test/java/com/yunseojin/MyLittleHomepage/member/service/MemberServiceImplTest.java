package com.yunseojin.MyLittleHomepage.member.service;

import com.yunseojin.MyLittleHomepage.MyLittleHomepageApplication;
import com.yunseojin.MyLittleHomepage.member.dto.MemberRequest;
import com.yunseojin.MyLittleHomepage.member.entity.MemberEntity;
import com.yunseojin.MyLittleHomepage.member.repository.MemberRepository;
import com.yunseojin.MyLittleHomepage.util.PasswordUtil;
import com.yunseojin.MyLittleHomepage.util.enumtype.MemberType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
        var res1 = memberService.resister(req);
        var res2 = memberService.resister(req);
        var dbMember = memberRepository.findByLoginId(req.getLoginId());
        // then
        assertTrue(res1);
        assertFalse(res2);
        assertEquals(req.getNickname(), dbMember.getNickname());
        assertTrue(PasswordUtil.checkPassword(req.getPassword(), dbMember.getPassword()));
        assertEquals(MemberType.NORMAL, dbMember.getMemberType());
    }
}